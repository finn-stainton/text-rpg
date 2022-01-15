package characters;

import openable.*;
import valuables.*;
import wieldable.*;
import pickups.Pickup;
import gameplay.Inventory;
import food.Food;

/**
 * Player Class
 * <h3>Extends Character</h3>
 * <p>Player class is the game's representation of the user. It navigates through a maze of rooms
 * picking up, admiring, eating and battling monsters with the end goal of finding the exit (in the final room).</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class Player extends Character {
    private int confidence;
    private String name;
    private Wieldable weapon;
    private Inventory inventory;
    private String armour;

    /**
     * Constructor sets the following params plus an empty inventory and weapon to fistsofury.
     * @param name String of player's display name
     * @param armour String description of player's armour
     * @param health int of player's health
     * @param confidence int of player's confidence
     */
    public Player(String name, String armour, int health, int confidence){
        super(name, health);
        setName(name);
        setArmour(armour);
        setConfidence(confidence);
        setWeapon(new FistsOfFury("FistsOfFury", 10, 25));
        setInventory(new Inventory());
    }

    //Getters and Setters
    private int getConfidence() {
        return confidence;
    }
    private void setConfidence(int confidence) {
        this.confidence = (confidence > 0) ? confidence : 0;
    }
    public String getName() {
        return name;
    }
    private void setName(String name) {
        this.name = name;
    }
    public Wieldable getWeapon() {
        return weapon;
    }
    private void setWeapon(Wieldable weapon) {
        this.weapon = weapon;
    }
    public Inventory getInventory() {
        return inventory;
    }
    private void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
    private String getArmour() {
        return armour;
    }
    private void setArmour(String armour) {
        this.armour = armour;
    }

    /**
     * dealAttackDamage calculates the damage a player can inflict on it's enemies by
     * damage = weapon strength + weapon strength * player's confidence / 100
     * @return int damage
     */
    protected int dealAttackDamage(){
        return weapon.getStrength() + weapon.getStrength()*this.getConfidence()/100;
    }

    /**
     * defendAttack calculates and returns damage from the enemy. Health is decrease by damage and confidence by half damage
     * @param enemy object
     * @return int damage infected to player
     */
    public int defendAttack(Character enemy){
        int damage = enemy.dealAttackDamage();
        setHealth(getHealth() - damage);
        setConfidence(getConfidence() - damage/2);
        return damage;
    }

    /**
     * open checks if user has a chest and key, if so, opens, removes and add content of chest to inventory.
     * It then deletes the chest and key from player's inventory.
     * @return String result of operation
     */
    public String open(){
        Inventory inventory = getInventory();
        //Check if any chest is in player's inventory
        if(inventory.select("TreasureChest") != null || inventory.select("WarChest") != null){
            //Add ether treasure or war chest to openable variable
            Openable openable = (inventory.select("TreasureChest") != null) ? (Openable) inventory.select("TreasureChest") : (Openable) inventory.select("WarChest");
            //Check if chest is locked
            if (openable.isLocked()){
                //Check if any opener (key/lockpick) is in user's inventory
                if(inventory.select("Key") != null || inventory.select("LockPick") != null){
                    //Add ether Key or LockPick to opener variable
                    Opener opener = (inventory.select("Key") != null) ? (Opener) inventory.select("Key") : (Opener) inventory.select("LockPick");
                    //Open, remove contents and delete opener and openable
                    openable.unlockWith(opener);
                    inventory.add(openable.getContent());
                    inventory.remove(opener);
                    inventory.remove(openable);
                    return "Brave " + this.getName() + " opens " + openable.getDescription() + " with a " + opener.getDescription() + " and finds " +
                            openable.getContent().getDescription() + ".";
                } else{ //Opener not in inventory
                    return this.getName() + " tries to open " + openable.getDescription() + " but doesn't have a key.";
                }
            } else{ //Openable is already unlocked
                inventory.add(openable.getContent());
                inventory.remove(openable);
                return "Added " + openable.getContent().getDescription() + " to inventory.";
            }
        } else{ //Openable not in inventory
            return "Don't have a Treasure or WarChest in inventory.";
        }
    }

    /**
     * admire checks if user has and can admire object, if so, the player's confidence is increase
     * by the valuable's value if not already consumed. Valuable can only be admire once.
     * @param attribute String id of valuable object
     * @return String result of operation
     */
    public String admire(String attribute){
        if(this.getInventory().select(attribute) != null){
            Pickup admire = this.getInventory().select(attribute);
            if(admire instanceof Valuable){
                Valuable valuable = (Valuable) admire;
                if(! valuable.isConsumed()){//Admire valuable
                    setConfidence(getConfidence() + valuable.getValue());
                    valuable.setConsumed(true);
                    return this.getName() + " admires " + admire.getDescription() + " and their confidence increases by " + valuable.getValue() + "." ;
                } else { //Item has already been admired
                    return  admire.getDescription() + " has already been admired.";
                }
            } else { //Item not admirable
                return "Can't admire " + attribute + ".";
            }
        } else { //Item not in Inventory
           return attribute + " not in Inventory.";
        }
    }

    /**
     * mobile enables the user to view social media(not really) during the game.
     * Player's confidence is increased by the mobile's value but can only be used once.
     * @return String result of operation
     */
    public String mobile(){
        if(this.getInventory().select("mobile") != null){
            Mobile mobile = (Mobile) this.getInventory().select("mobile");
            if(! mobile.isConsumed()){//Look up social media
                setConfidence(getConfidence() + mobile.getValue());
                mobile.setConsumed(true);
                return this.getName() + " uses " + mobile.getDescription() + " for ages...";
            } else { //Mobile has already been admired
                return  getName() + " is now bored of social media.";
            }
        } else { //Mobile not in Inventory
            return "Mobile not in Inventory.";
        }
    }

    /**
     * eat checks if user has and can eat object, if so, the player's health is increase
     * by the food's value if not already consumed. The food is then removed from player's inventory.
     * @param attribute String id of food object
     * @return String result of operation
     */
    public String eat(String attribute){
        if(getInventory().select(attribute) != null){ //check if item in Inventory
            Pickup pickup = this.getInventory().select(attribute);
            if(pickup instanceof Food){ //check if item is eatable (Food)
                Food food = (Food) pickup;
                if(!food.isConsumed()){ //check if food has been eaten
                    setHealth(getHealth() + food.getHealth());
                    food.setConsumed(true);
                    getInventory().remove(food); //Remove food item from inventory
                    return getName() + " eats the " + food.getID() + " and increasing their health by " + food.getHealth() + ".";
                }
                else{ //Food has been eaten
                    return  food.getDescription() + " is gone!";
                }
            } else{ //Item not eatable
                return "Can't eat " + attribute;
            }
        } else{ //Item not in Inventory
            return attribute + " not in Inventory.";
        }
    }

    /**
     * wield changes the player's weapon to one present in their inventory. Player can always wield "fistsofury".
     * @param attribute string id of wieldable object
     * @return String result of operation
     */
    public String wield(String attribute){
        if(attribute.equalsIgnoreCase("fistsoffury")){ //if attribute fistsOfFury
            Wieldable wieldable = new FistsOfFury("FistsOfFury", 10, 25);
            setWeapon(wieldable);
            return "Brave " + getName() + " wields " + wieldable.getID();
        } else if(this.getInventory().select(attribute) != null){ //if attribute in inventory
            Pickup pickup = this.getInventory().select(attribute);
            if(pickup instanceof Wieldable){ //if item is wieldable
                Wieldable wieldable = (Wieldable) pickup;
                setWeapon(wieldable);
                return "Brave " + getName() + " wields " + wieldable.getID();
            } else{ //Item not wieldable
                return "Can't wield " + attribute;
            }
        } else{ //Item not in inventory
            return attribute + " not in Inventory.";
        }
    }

    /**
     * stats returns player's; name, armour, health, confidence, weapon and items in their inventory.
     * @return String
     */
    public String stats(){
        String output = toString() + "\n" +
                "Brave " + getName() + " is carrying the following items: " + getInventory().toString();
        return output;
    }

    /**
     * toString representation of player object
     * Example:
     * "Player is wearing Shiny Armour
     *  Health: 100 Confidence: 50 Wielding: FistsOfFury"
     *
     * @return String representation of Player object.
     */
    @Override
    public String toString(){
        return "\n" + getName() + " is wearing " + getArmour() + "\n" +
                "Health: " + getHealth() + " Confidence: " + getConfidence() + " Wielding: " + getWeapon();
    }

}
