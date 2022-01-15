package characters;

/**
 * Ghost Class
 * <h3>Extends Monster</h3>
 * <p>note: this object is only used if a room in the world file doesn't contain a monster</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class Ghost extends Monster {

    /**
     * Constructor takes the following param and sets the damage to 5, description to Grey Ghost and health to 10
     * (You can tweak the settings to your desire)
     * @param probability int from 0-100 percent chance of the monster appearing
     */
    public Ghost(int probability){
        super(probability, 5, "Grey Ghost", 10);
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
