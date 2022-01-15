package characters;

/**
 * Zombie Class
 * <h3>Extends Monster</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public class Zombie extends Monster {

    /**
     * Constructor takes the following param and sets the damage to 40, description to Green Dragon and health to 100
     * (You can tweak the settings to your desire)
     * @param probability int from 0-100 percent chance of the monster appearing
     */
    public Zombie(int probability){
        super(probability, 15, "Slow moving zombie", 30);//wrong stats
    }

    /**
     * toString
     * @return String description of object^
     */
    @Override
    public String toString(){
        return "Zombie";
    }
}
