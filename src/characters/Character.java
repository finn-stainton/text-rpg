package characters;

import entities.Entity;

/**
 * Character Abstract Class
 * <p>Is the base of all characters with variable health and abstract methods; dealAttackDamage and defendAttack</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Character extends Entity {
    private int health;

    /**
     * Constructor sets up character's health
     * @param description String which describes the character
     * @param health int of character's health
     */
    public Character(String description, int health){
        super(description);
        setHealth(health);
    }

    //Getters and Setters
    public int getHealth() {
        return health;
    }
    public void setHealth(int health) {
        //if health <= 0, set to 0
        this.health = (health > 0) ? health : 0;
    }

    //Abstract methods
    protected abstract int dealAttackDamage();
    public abstract int defendAttack(Character enemy);
}
