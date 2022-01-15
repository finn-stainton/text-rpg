package pickups;

/**
 * Consumable Abstract Class
 * <h3>Extends Pickup</h3>
 * <p>Is the super class of {@code Food} and {@code Valuable} which can be consumed.</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Consumable extends Pickup {
    private boolean consumed;

    /**
     * Constructor sets the following params plus consumed to false
     * @param description String which describes the consumable
     */
    public Consumable(String description){
        super(description);
        setConsumed(false);
    }

    //Getters and setters
    public boolean isConsumed() {
        return consumed;
    }
    public void setConsumed(boolean consumed) {
        this.consumed = consumed;
    }
}
