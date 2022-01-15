package wieldable;

import pickups.Pickup;

/**
 * Wieldable Abstract Class
 * <h3>Extends Pickup</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Wieldable extends Pickup {
    private int low;
    private int high;
    private int strength;

    /**
     * Constructor sets the following params plus {@code Wieldable} strength with hit method
     * @param description String which describes the {@code Wieldable}
     * @param low int value of minimum possible weapon strength
     * @param high int value of maximum possible weapon strength
     */
    public Wieldable(String description, int low, int high){
        super(description);
        setLow(low);
        setHigh(high);
        setStrength(hit());
    }

    //Getters and setters
    public int getLow() {
        return low;
    }
    public void setLow(int low) {
        this.low = low;
    }
    public int getHigh() {
        return high;
    }
    public void setHigh(int high) {
        this.high = high;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }

    /**
     * hit calculates weapon strength but getting a random number from {@code Entity} getRandomInteger()
     * @return int value representing weapon strength
     */
    public int hit(){
        return getRandomInteger(getLow(), getHigh());
    }
}
