package entities;

import java.util.Random;
import java.lang.String;

/**
 * Entity class
 * <p>Is the default object of this game, with every object having a description and id</p>
 *
 * @author finnstainton 17982742
 * @version 1.0
 */
public abstract class Entity {
    private String description;
    private String id;

    /**
     * Default Constructor sets the id to a simple version of the class's name.
     */
    public Entity(){
        this.id = this.getClass().getSimpleName();
    }

    /**
     * Constructor sets the following param plus id to a simple version of the class's name.
     * @param description String describing the object
     */
    public Entity(String description){
        setDescription(description);
        this.id = this.getClass().getSimpleName();
    }

    //Getters and setters
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getID() {
        return id;
    }

    /**
     * getRandomInteger implements the Random method from java.util package
     * @param x int of the minimum possible value that can be returned
     * @param y int of the maximum possible value that can be returned
     * @return int value between x and y
     */
    protected int getRandomInteger(int x, int y){
        return new Random().nextInt(y-x) + x;
    }

    /**
     * compareID takes an id and the id of the object it's implemented in.
     * @param id String of another object's id
     * @return boolean true: id's are same ignoring case, else false
     */
    protected boolean compareID(String id){
        return getID().equalsIgnoreCase(id);
    }

    /**
     * toString
     * @return String of object's ID
     */
    @Override
    public String toString(){
        return getID();
    }
}
