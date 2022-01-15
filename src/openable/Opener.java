package openable;

import pickups.Pickup;

/**
 * Opener class
 * <h3>Extends Pickup</h3>
 * <p>Has a constructor for LockPick and Key subclasses to initialize their descriptions.</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Opener extends Pickup{

    /**
     * Constructor sets the following params:
     * @param description String which describes the object
     */
    public Opener(String description){
        super(description);
    }
}
