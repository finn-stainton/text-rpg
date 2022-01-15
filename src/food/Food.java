package food;

import pickups.Consumable;

/**
 * Food Abstract Class
 * <h3>Extends Consumable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Food extends Consumable{
    private int health;

    /**
     * Constructor sets the following params:
     * @param description String which describes the object
     * @param health int of health benefit the object proves
     */
    public Food(String description, int health){
        super(description);
        setHealth(health);
    }

    //Getters and setters
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        this.health = health;
    }
}
