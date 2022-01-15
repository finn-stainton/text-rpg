package valuables;

import pickups.Consumable;

/**
 * Valuable Abstract Class
 * <h3>Extends Consumable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Valuable extends Consumable{
    private int value;

    /**
     * Constructor sets the following params:
     * @param description String which describes the object
     * @param value int value of object benefit to confidence (implemented further along the line...)
     */
    public Valuable(int value, String description){
        super(description);
        setValue(value);
    }

    //Getters and setters
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
        this.value = value;
    }
}
