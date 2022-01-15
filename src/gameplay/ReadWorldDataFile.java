package gameplay;

/**
 * RealWorldDataFile reads data from the worlddata/simpleworld.txt file to instantiate a World object
 * 
 * Before uncommenting this code be sure all subclasses of Entity have been (at least partially) implemented (Okay Doc.)
 *
 * @author Auckland University of Technology
 * @version 1.0
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.lang.Integer;
import characters.*;
import entities.Room;
import food.*;
import openable.*;
import pickups.*;
import valuables.*;
import wieldable.*;

public class ReadWorldDataFile {

	public static final boolean outputToConsole = false;
	public static final String worldDataFilePath = "worlddata/";

	public enum EntityIdentifier 
	{
		goldbars,jewel,coin,ring,moneybag,chalice,mobile,
		warchest,treasurechest,
		sword,
		lockpick,
		zombie,dragon,ogre,
		roastboar,mead,
		axe,key,
		bread
	}

	
	public enum WorldDataFileTag 
	{
		numberofpickups,monster,numberofrooms,connect,roomend,description,startroom,endquest;

		public static WorldDataFileTag fromFile(String line) 
		{
			line = line.replace("@", "").toLowerCase();
			return WorldDataFileTag.valueOf(line);		
		}
	}
	


	static private void log(String logText)
	{
		if(outputToConsole)
		{
			System.out.println(logText);
		}
	}

	private static Pickup parsePickUpData(String text)
	{	
		log(text);
		String[] tokens = (text.trim().toLowerCase()).split(" ");
		EntityIdentifier token = EntityIdentifier.valueOf(tokens[0]);

		switch(token)
		{

		//valuables
		case coin:
			return new Coin("A small collection of coins",Integer.parseInt(tokens[1]));	
		case chalice:
			return new Chalice("A gold chalice",Integer.parseInt(tokens[1]));	
		case moneybag:
			return new MoneyBag("A bag of money",Integer.parseInt(tokens[1]));
		case ring:
			return new Ring("An ancient silver ring",Integer.parseInt(tokens[1]));
		case goldbars:
			return new GoldBars("several gold bars in a neat pile",Integer.parseInt(tokens[1]));		
		case jewel:
			return new Jewel("the shiny jewel",Integer.parseInt(tokens[1]));
		case mobile:	
			return new Mobile("Apple iPhone",Integer.parseInt(tokens[1]));
		case treasurechest:					
			Valuable v = (Valuable) parsePickUpData(tokens[2]+" "+tokens[3]);
			return new TreasureChest(v);
		case warchest:
			Wieldable w = (Wieldable) parsePickUpData(tokens[2]);
			return new WarChest(w);
		
		//wieldable pickups
		case axe:
			return new Axe("Heavy axe",15,30);
		case sword:
			return new Sword("Sword",30,39);
		
		//openers	
		case lockpick:			
			return new LockPick("Lock pick");
		case key:
			return new Key("Small key");

		//food
		case roastboar:
			return new RoastBoar("Roast Boar", Integer.parseInt(tokens[1]));
			
		case mead:
			return new Mead("Mead", Integer.parseInt(tokens[1]));

		case bread:
			return new Bread("Bread", Integer.parseInt(tokens[1]));
			
		default:
			break;

		}
		System.out.println("Warning: I couldn't find "+token);
		return null;
	}




	private static int[] parseConnectingRoomsData(String text)
	{

		StringTokenizer st = new StringTokenizer(text);
		int nTokens = st.countTokens();

		int[] passages = new int[nTokens];


		for(int i=0;i<nTokens;i++)
		{
			passages[i] = Integer.parseInt(st.nextToken());
		}

		return passages;
	}

	private static Monster parseMonsterData(String text)
	{

		String[] tokens = text.split(" ");
		String monsterKind = tokens[0];

		int probability = Integer.parseInt(tokens[1]);

		EntityIdentifier token  = EntityIdentifier.valueOf(monsterKind.toLowerCase());
		switch(token)
		{
		case zombie:
			return new Zombie(probability);

		case ogre:
			return new Ogre(probability);

		case dragon:
			return new Dragon(probability);

		default:
			return null;

		}
	}


	private static World readFile(String levelDataFile) throws IOException
	{

		Scanner fileScan = new Scanner(new File(levelDataFile));

		log("Initialising game map from file: "+levelDataFile);

		//read and print the meta tag @numberofrooms 
		log("meta tag: "+fileScan.nextLine());
		//read the number of rooms
		int numberOfRooms = Integer.parseInt(fileScan.nextLine());

		log("Number of rooms: "+numberOfRooms);

		Room[] rooms = new Room[numberOfRooms];
		Room startRoom = null;

		for(int i=0;i<numberOfRooms;i++)
		{
			rooms[i] = new Room();
			rooms[i].setDescription("Empty Room #: "+(i+1));
		}

		for(int i = 0;i<numberOfRooms;i++)
		{			 			
			Room room = rooms[i];

			//now process each of the metatags in the file...
			boolean processingRoomDone=false;
			do
			{
				String line = fileScan.hasNext()?fileScan.nextLine():"";	

				log("meta tag: "+line);
				WorldDataFileTag tag = WorldDataFileTag.fromFile(line);

				switch(tag)
				{
				case endquest:
					room.setFinalRoom(true);
					break;
				case connect:
					//read and print the meta tag for connections and connections text (as an array).				
					String connectingRoomText = fileScan.nextLine();
					log(connectingRoomText);
					int[] connectingRoomsIndicies = parseConnectingRoomsData(connectingRoomText);
					int nConnectingRooms = connectingRoomsIndicies.length;
					Room[] connectingRooms = new Room[nConnectingRooms];
					for(int j=0;j<nConnectingRooms;j++)	
					{
						connectingRooms[j] = rooms[connectingRoomsIndicies[j]-1];	
					}
					room.setConnectingRooms(connectingRooms);
					break;
				case startroom:
					startRoom = room;
					break;
				case description:
					//read and print the meta tag for description and description text.
					String description = fileScan.nextLine();			
					log(description);
					room.setDescription(description);
					break;
				case monster:
					//read and print the meta tag @numberofrooms 
					String monsterText = fileScan.nextLine();
					room.setMonster(parseMonsterData(monsterText));
					break;
				case numberofpickups:
					//How many picksup in the room?
					int nPickUps = Integer.parseInt(fileScan.nextLine());
					log("Number of pickups: "+nPickUps);
					Inventory pickups = new Inventory();

					for(int j=0;j<nPickUps;j++)
					{
						String text = fileScan.nextLine();
						Pickup aPickup = parsePickUpData(text);
						pickups.add(aPickup);
						log(" "+text);
					}
					room.setPickupsInRoom(pickups);

					break;
				case roomend:					
				default:
					processingRoomDone=true;
					break;

				}
			}while(!processingRoomDone);
		}

		log("World has successfully loaded");
		fileScan.close();
		return new World(startRoom);

	}

	public static World simpleWorld()
	{
		String filename = worldDataFilePath+"simpleworld.txt";
		try 
		{
			return ReadWorldDataFile.readFile(filename);

		} catch (IOException e) 
		{
			System.err.println("Could not find file "+filename);
			e.printStackTrace();
		}
		return null;
	}

}