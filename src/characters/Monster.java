package characters;

import java.util.Random;

/**
 * Monster Class
 * <h3>Extends Character</h3>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Monster extends Character{
    private final int LOW = 1;
    private final int HIGH = 10;
    private int probability;
    private int damage;

    /**
     * Constructor sets the following params
     * @param probability int from 0-100 percent chance of the monster appearing
     * @param damage int amount of damage monster can inflict
     * @param description String of monster's description
     * @param health int of monster's health
     */
    public Monster(int probability, int damage, String description, int health){
        super(description, health);
        setDamage(damage);
        setProbability(probability);
    }

    //Getters and Setters
    public int getProbability() {
        return probability;
    }
    private void setProbability(int probability) {
        this.probability = probability;
    }
    public int getDamage() {
        return damage;
    }
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Deal attack damage calculates the amount of damage the monster inflicts.
     * d = damage + 1 < random < 10
     * @return int damage inflicted
     */
    @Override
    protected int dealAttackDamage(){
        return getDamage() + getRandomInteger(LOW, HIGH);
    }

    /**
     * Defend Attack simulates an incoming attack from an enemy character.
     * Reduces monsters health by damage inflicted.
     * @param enemy Character Object
     * @return int damage inflicted to this object
     */
    @Override
    public int defendAttack(Character enemy){
        int damage = enemy.dealAttackDamage();
        this.setHealth(getHealth() - damage);
        return damage;
    }

    /**
     * appears if a random number is less than or equal to monster's probability and monster's health greater than zero.
     * @return boolean if monster appear in a room or not
     */
    public boolean appears(){
        boolean appear = false;
        int x = new Random().nextInt(100-1) + 1;

        if(x<=probability && getHealth() != 0){
            appear = true;
        } else{
            appear = false;
        }

        return appear;
    }
}
