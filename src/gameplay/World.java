package gameplay;

import characters.*;
import entities.Room;
import pickups.Pickup;
import java.util.Scanner;
import java.lang.String;

/**
 * World Class
 * <p>The user moves through a maze of rooms, battling monsters and collected objects to stay alive
 * with the end goal of finding the Exit!</p>
 *
 * @author finnstainton 17982742 and Auckland University of Technology
 * @version 1.0
 */
public class World {
    /**
     * Available game modes
     */
    private enum PlayMode {
        explore, battle
    }

    /**
     * Supported Commands
     * Contains an int representing the number of attributes the user must enter.
     * Example: door 2 or open chest key
     */
    private enum Command {
        attack(1), door(2), pickup(2), exit(1), describe(1), admire(2), eat(2), mobile(1), stats(1), wield(2), open(3), help(1), quit(1), cheat(1);

        private int elements;

        Command(int elements){
            this.elements = elements;
        }

        public int getElements() {
            return elements;
        }
    }

    /**
     * Supported Items
     */
    private enum Item {
        bread, mead, roastboar, axe, sword, fistsoffury, chalice, coin, goldbars, jewel, mobile, moneybag, ring, key, lockpick, treasurechest, warchest, chest
    }

    /**
     * Supported Opener (open cmd)
     */
    private enum OpenerItem{
        key
    }

    /**
     * Exit types
     */
    private enum Exit {
        quit, exit, defeated
    }

    //Variables
    private Player player;
    private Room startRoom;
    private Room currentRoom;
    private boolean gameInProgress;
    private PlayMode mode;
    private Exit exit;
    private Scanner scanner = new Scanner(System.in);
    private static final boolean outputToConsole = true;

    /**
     * Constructor sets the start room of the world
     * @param startRoom Room object set by main method
     */
    public World(Room startRoom) {
        setStartRoom(startRoom);
    }

    //Setters and Getters
    private Player getPlayer() {
        return player;
    }
    private void setPlayer(Player player) {
        this.player = player;
    }
    private Room getStartRoom() {
        return startRoom;
    }
    private void setStartRoom(Room startRoom) {
        this.startRoom = startRoom;
    }
    private Room getCurrentRoom() {
        return currentRoom;
    }
    private void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }
    private boolean isGameInProgress() {
        return gameInProgress;
    }
    private void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }
    private PlayMode getMode() {
        return mode;
    }
    private void setMode(PlayMode mode) {
        this.mode = mode;
    }
    private Exit getExit() {
        return exit;
    }
    private void setExit(Exit exit) {
        this.exit = exit;
    }
    static private void elog(String logText) {
        if (outputToConsole) {
            System.err.println(logText);
        }
    }

    /**
     * Play
     * Welcomes the player, then loops until player is defeated, quits or wins
     * @param player object set by main method
     */
    private void play(Player player) {
        //Set up player
        setPlayer(player);
        System.out.println("Welcome " + player.getName());

        //Player toString
        System.out.println(player);

        setCurrentRoom(getStartRoom());

        this.onEnterRoom();

        while (isGameInProgress()) {
            switch (getMode()) {
                case explore:
                    readyExploreMode();
                    break;
                case battle:
                    readyBattleMode();
                    break;
            }
        }

        if(!isGameInProgress() && getExit() == Exit.defeated) { //Player has been defeated
            System.out.println(player);
            System.out.println("Alas! " + player.getName() + " has been defeated!\n" +
                    "You have failed your quest.");
        } else if(!isGameInProgress() && getExit() == Exit.quit) { //Player has quit game
            System.out.println(player.getName() + " has left this world.\n" +
                    "Your quest has ended short.");
        } else if(!isGameInProgress() && getExit() == Exit.exit) { //Player has found the exit, ya!
            System.out.println("...and behold, found it.\n" + "Well done brave " + player.getName() + ". \n" +
                    "Your quest has now ended.");
        } else { //Keep playing
            System.out.println(player);
        }
    }

    /**
     * OnEnterRoom
     * Prints a description of room, sets the current monster.
     * Also changes and executes the mode depending if monster exists
     */
    private void onEnterRoom(){
        //Print short description of current room
        System.out.println(currentRoom.getDescription());

        Monster monster = null;

        //Check if current room has monster
        if(currentRoom.getMonster() != null){
            monster = currentRoom.getMonster();
        } else{ //Create a ghost with 50% chance of appearing
            monster = new Ghost(50);
            currentRoom.setMonster(monster);
        }

        if (monster != null){
            boolean appears = monster.appears();
            if(appears){ //If monster appear, go into battle mode
                System.out.println("A " + monster + " draws hither! \n" + player.getName() + " wields " + player.getWeapon()
                        + " and is ready for battle!");
                setMode(PlayMode.battle);
                readyBattleMode();
            } else{ //Go into explore mode
                setMode(PlayMode.explore);
                readyExploreMode();
            }
        } else{ //Should never happen, but just in case
            elog("World File Error: null monster");
        }
    }

    /**
     * processExploreUserInput
     * Processes accepted user commands for explore mode.
     * Commands: door n, pickup item, exit, describe, admire valuable, eat food, mobile, stats,
     * wield weapon, wield fistsoffury, open chest key, help, quit
     * @param inputCmd String from user
     */
    private void processExploreUserInput(String inputCmd) {
        String[] tokens = inputCmd.toLowerCase().split(" ");
        String attribute = "";
        String sopener = "";
        int door = 0;

        //Check if user entered command and is supported in explore mode
        try{
            Command token = Command.valueOf(tokens[0]);

            if(Command.valueOf(tokens[0]).getElements() == 2){ //Check if command needs two elements
                if(tokens.length == 2){ //Check if user entered two elements
                    if(tokens[0].equalsIgnoreCase("door")){ //Special case: door command's attribute must be an int
                        try{
                            door = Integer.parseInt(tokens[1]);
                        } catch (Exception e){ //Attribute not int
                            System.out.println("Please enter a door number.");
                            return;
                        }
                    } else{ //Parse all supported commands that are not "door"
                        try { //Check if attribute entered part of Item enum
                            Item item = Item.valueOf(tokens[1]);
                            attribute = tokens[1];
                        } catch (Exception e){ //Attribute unknown
                            System.out.println("Unknown Item.");
                            return;
                        }
                    }
                } else{ //No attribute entered
                    System.out.println("Please enter attributes for that command.");
                    return;
                }
            } else if(Command.valueOf(tokens[0]).getElements() == 3){ //Check if command needs three elements
                if(tokens.length == 3){ //Check if user entered three elements
                    try { //Check if attributes enter part of Item and Opener enums respectively
                        Item item = Item.valueOf(tokens[1]);
                        attribute = tokens[1];
                        OpenerItem opener = OpenerItem.valueOf(tokens[2]);
                        sopener = tokens[2];
                    } catch (Exception e){ //Attributes unknown
                        System.out.println("Unknown Items.");
                        return;
                    }
                } else{ //Not enough attributes entered
                    System.out.println("Please enter attributes for that command.");
                    return;
                }
            }

            switch (token) {
                case door:
                    Room rooms[] = currentRoom.getConnectingRooms();
                    try{ //check if room exists
                        Room room = rooms[door-1];
                        setCurrentRoom(room);
                        System.out.println(player.getName() + " bravely opens door " + door);
                        onEnterRoom();
                    } catch (Exception e){
                        System.out.println("Can't find door " + door);
                    }
                    break;

                case pickup:
                    //Check if item is on the floor
                    if(currentRoom.getPickupsInRoom().select(attribute) != null) {
                        Pickup pickup = currentRoom.getPickupsInRoom().select(attribute);
                        //Check if item is in player's Inventory
                        if (player.getInventory().select(attribute) == null) {
                            //Add item to player's Inventory
                            player.getInventory().add(pickup);
                            //Remove item from Room
                            currentRoom.getPickupsInRoom().remove(pickup);
                            //Display to player
                            System.out.println(player.getName() + " picks up " + pickup.getDescription());
                        } else { //Item is already in Inventory
                            System.out.println( pickup.getID() + " is already in Inventory");
                        }
                    } else { //Item not on the floor
                        System.out.println("Can't find " + attribute);
                    }
                    break;

                case exit:
                    //Exit if final room
                    System.out.println(player.getName() + " searched everywhere for the exit...");
                    if(currentRoom.isFinalRoom()){
                        setGameInProgress(false);
                        setExit(Exit.exit);
                        return;
                    } else{ //not final room
                        System.out.println("...But couldn't find it in this room");
                    }
                    break;

                case describe:
                    //Room description, items and doors
                    System.out.println(currentRoom.getDescription());
                    for (Pickup p : currentRoom.getPickupsInRoom().getItems()) {
                        System.out.println("There is " + p.getID() + " on the floor.");
                    }
                    System.out.println("There are " + currentRoom.getConnectingRooms().length + " doors.");
                    break;

                case admire:
                    //Admire item in inventory (player.admire())
                    System.out.println(player.admire(attribute));
                    break;

                case eat:
                    //Eat item in inventory (player.eat())
                    System.out.println(player.eat(attribute));
                    break;

                case mobile:
                    //admire social media (player.mobile) not really
                    System.out.println(player.mobile());
                    break;

                case stats:
                    //Display player's statistics
                    System.out.println(player.stats());
                    break;

                case wield:
                    //Wield weapon from inventory (player.wield)
                    System.out.println(player.wield(attribute));
                    break;

                case open:
                    //The contents of the chest is placed in the player’s inventory and both the chest and key are removed.
                    //Check if user entered correct command (open chest key)
                    if(attribute.equalsIgnoreCase("chest") && sopener.equalsIgnoreCase("key")){
                        System.out.println(player.open());
                    } else{ //Unsupported attribute to 'open' command
                        System.out.println("Unsupported attributes.");
                    }
                    break;

                case help:
                    //Display supported commands
                    System.out.println("Supported Commands: door <n>, pickup <item>, exit, describe, admire <valuable>, \n" +
                            "eat <food>, mobile, stats, wield <weapon>, open chest key, help, quit");
                    break;

                case quit:
                    //Finish game
                    setGameInProgress(false);
                    setExit(Exit.quit);
                    break;

                case cheat:
                    //User found the exit some how...
                    System.out.println(player.getName() + " searched everywhere for the exit...");
                    setExit(Exit.exit);
                    setGameInProgress(false);
                    break;

                default:
                    //Display error message
                    System.out.println("Unsupported Explore Mode Command, please try again.");
                    break;
            }
        } catch (Exception e){
            //Unknown command
            System.out.println("What does \"" + tokens[0] + "\" mean?");
        }
    }

    /**
     * ProcessBattleUserInput
     * Processes accepted user commands for battle mode.
     * Commands: attack, wield weapon, wield fistsoffury, help, quit
     * @param inputCmd String from user
     */
    private void processBattleUserInput (String inputCmd){
        //Process input String
        String[] tokens = inputCmd.toLowerCase().split(" ");
        String attribute = "";

        try {
            Command token = Command.valueOf(tokens[0]);

            if(Command.valueOf(tokens[0]).getElements() == 2){ //Check if command needs two elements
                if(tokens.length >= 2){ //Check if user has entered attribute
                    try {
                        Item item = Item.valueOf(tokens[1]);
                        attribute = tokens[1];
                    } catch (Exception e){ //Unknown attribute
                        System.out.println("Unknown Item");
                        return;
                    }
                } else{ //No attributes entered
                    System.out.println("Please enter an attribute for that command");
                    return;
                }
            }

            //Switch depending on command
            switch (token) {
                case attack:
                    //Player attacks!
                    attack();
                    break;

                case wield://Wield weapon from inventory (player.wield)
                    System.out.println(player.wield(attribute));
                    break;

                case help:
                    //Displays supported battle mode commands
                    System.out.println("Available Commands: attack, wield, help, quit");
                    break;

                case quit:
                    //User quits game
                    setGameInProgress(false);
                    setExit(Exit.quit);
                    break;

                default:
                    System.out.println("Unsupported Battle Mode Command, please try again");
                    break;

            }
        } catch (Exception e){ //Unknown command (Command enum)
            System.out.println("Unsupported Command, please try again.");
        }
    }

    /**
     * ReadyExploreMode
     * Accepts input from user and distributes it to processExploreUserInput().
     */
    private void readyExploreMode () {
        //Set game in progress
        this.gameInProgress = true;

        //Get user input
        System.out.print("?>");
        String input = scanner.nextLine();

        //Process user input
        processExploreUserInput(input);
    }

    /**
     * ReadyBattleMode
     * Accepts input from user and distributes it to processBattleUserInput().
     * Ends game if player defeated, changes to explore mode if monster defeated, else continue in battle mode.
     */
    private void readyBattleMode () {
        //Get user input
        System.out.print("?>");
        String input = scanner.nextLine();

        //Process user input
        processBattleUserInput(input);

        //Check if player and monster alive
        if(player.getHealth() <= 0){ //Player defeated
            setGameInProgress(false);
            setExit(Exit.defeated);
        } else if(currentRoom.getMonster().getHealth() <= 0){ //Monster defeated
            System.out.println("Brave " + player.getName() + " has defeated the " + currentRoom.getMonster().getDescription() + "!");
            setGameInProgress(true);
            setMode(PlayMode.explore);
        } else{ //Continue battling
            setGameInProgress(true);
            setMode(PlayMode.battle);
            System.out.println(player);
        }
    }

    /**
     * Attack
     * Calculates and subtracts damage to both player and monster.
     */
    private void attack() {
        System.out.println(player.getName() + " attacks " + currentRoom.getMonster().getDescription());

        //Calculate damage to monster
        int monsterDamage = currentRoom.getMonster().defendAttack(player);
        System.out.println("The " + currentRoom.getMonster().getDescription() + " sustains " + monsterDamage + " damage");

        //Check if monster is defeated
        if(currentRoom.getMonster().getHealth() <= 0){ // monster defeated, return to readyBattleMode()
            return;
        } else{
            System.out.println("The " + currentRoom.getMonster() + " attacks!");
            //Calculate damage to player
            int playerDamage = player.defendAttack(currentRoom.getMonster());
            System.out.println(player.getName() + " has sustained " + playerDamage + " damage!");
        }
    }

    /**
     * Main
     * Sets up world, player and calls 'presses' play. Lets go
     */
    public static void main(String[] args)
    {
        World world = ReadWorldDataFile.simpleWorld();
        Player player = new Player("Player 1", "Shiny Armour", 100, 50);
        world.play(player);

        world.scanner.close();
        System.exit(0);
    }

}