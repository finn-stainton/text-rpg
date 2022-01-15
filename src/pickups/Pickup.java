package pickups;

import entities.Entity;

/**
 * Pickup Abstract Class
 * <h3>Extends Entity</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Pickup extends Entity{

    /**
     * Constructor sets the following params:
     * @param description String which describes the {@code Pickup}
     */
    public Pickup(String description){
        super(description);
    }
}
