package entities;

import characters.Monster;
import gameplay.Inventory;

/**
 * Room Class
 * <p>Rooms can contain pickups and characters as the player navigates through a world of multiple rooms</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class Room extends Entity{
    private Monster monster;
    private Inventory pickupsInRoom;
    private Room[] connecting;
    private boolean finalRoom;

    /**
     * Default constructor
     */
    public Room(){
        setMonster(null);
        setPickupsInRoom(null);
        setConnectingRooms(null);
        setFinalRoom(false);
    }

    /**
     * Constructor
     * @param description String which describes the room
     * @param pickupsInRoom Inventory object of items able to be picked up
     * @param connectingRooms Room array of all connecting rooms
     */
    public Room(String description, Inventory pickupsInRoom, Room[] connectingRooms){
        super(description);
        setPickupsInRoom(pickupsInRoom);
        setConnectingRooms(connectingRooms);
    }

    //Setters and getters
    public Monster getMonster() {
        return this.monster;
    }
    public void setMonster(Monster monster) {
        this.monster = monster;
    }
    public Inventory getPickupsInRoom() {
        return pickupsInRoom;
    }
    public void setPickupsInRoom(Inventory pickupsInRoom) {
        this.pickupsInRoom = pickupsInRoom;
    }
    public Room[] getConnectingRooms() {
        return connecting;
    }
    public void setConnectingRooms(Room[] connecting) {
        this.connecting = connecting;
    }
    public boolean isFinalRoom() {
        return finalRoom;
    }
    public void setFinalRoom(boolean finalRoom) {
        this.finalRoom = finalRoom;
    }
}
