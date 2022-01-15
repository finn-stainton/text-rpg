package openable;

import wieldable.Wieldable;

/**
 * WarChest Class
 * <h3>Extends Openable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class WarChest extends Openable {

    /**
     * Constructor sets the following params plus description to "War Chest"
     * @param wieldable Wieldable object which the openable contains
     */
    public WarChest(Wieldable wieldable){
        super("War Chest", wieldable);
    }

    /**
     * UnlockWith
     * @param opener Opener object to unlock the Openable object
     */
    @Override
    public void unlockWith(Opener opener){
        if(opener != null){
            setLocked(!LOCKED); //Not locked, p.s. locked == true
        } else{
            setLocked(LOCKED);
        }
    }

}
