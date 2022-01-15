package openable;

import pickups.Pickup;

/**
 * Openable Abstract Class
 * <h3>Extends Pickup</h3>
 * <p>Is an object which contains a {@code Valuable} object
 * and is locked by default which can be unlocked using a {@code Opener}.</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Openable extends Pickup{
    protected final boolean LOCKED = true;
    private Pickup content;
    private boolean locked;

    /**
     * Constructor sets the following params:
     * @param description String which describes the object
     * @param content Pickup(Valuable) object which the openable contains
     */
    public Openable(String description, Pickup content) {
        super(description);
        setContent(content);
        setLocked(LOCKED);
    }

    //Getters and setters
    public Pickup getContent() {
        return content;
    }
    public void setContent(Pickup content) {
        this.content = content;
    }
    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * unlockWith abstract method
     * @param opener Opener object to unlock the Openable object
     */
    public abstract void unlockWith(Opener opener);
}
