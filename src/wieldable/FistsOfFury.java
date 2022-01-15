package wieldable;

/**
 * FistsOfFury Class
 * <h3>Extends Wielable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class FistsOfFury extends Wieldable {

    /**
     * Constructor sets the following params:
     * @param description String which describes the {@code Wieldable}
     * @param low int value of minimum possible weapon strength
     * @param high int value of maximum possible weapon strength
     */
    public FistsOfFury(String description, int low, int high){
        super(description, low, high);
    }
}
