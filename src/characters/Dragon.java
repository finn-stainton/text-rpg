package characters;

/**
 * Dragon Class
 * <h3>Extends Monster</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class Dragon extends Monster {

    /**
     * Constructor takes the following param and sets the damage to 40, description to Green Dragon and health to 100
     * (You can tweak the settings to your desire)
     * @param probability int from 0-100 percent chance of the monster appearing
     */
    public Dragon(int probability) {
        super(probability, 40, "Green dragon", 100); //not sure on stats
    }

    /**
     * toString
     * @return String description of object^
     */
    @Override
    public String toString(){
        return this.getDescription();
    }
}
