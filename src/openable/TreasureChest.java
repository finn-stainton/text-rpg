package openable;

import valuables.Valuable;

/**
 * TreasureChest class
 * <h3>Extends Openable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class TreasureChest extends Openable{

    /**
     * Constructor sets the following params plus description to "Treasure Chest"
     * @param valuable Valuable object which the openable contains
     */
    public TreasureChest(Valuable valuable){
        super("Treasure Chest", valuable);
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
