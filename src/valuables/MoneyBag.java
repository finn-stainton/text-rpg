package valuables;

/**
 * Coin Class
 * <h3>Extends Valuable</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class MoneyBag extends Valuable {

    /**
     * Constructor sets the following params:
     * @param description String which describes the {@code Valuable}
     * @param value int value of object benefit to confidence (implemented further along the line...)
     */
    public MoneyBag(String description, int value){
        super(value, description);
    }
}
