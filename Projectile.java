import java.util.ArrayList;

/** The class Projectile represents a projectile fired by
 * a plant object. It is responsible for handling its 
 * movement and dealing damage to a zombie object.  
 *
 *  @author PieIsSpy
 *  @author rachell-code
 *  @version 1.1
 */
public class Projectile extends GameElement{

    /**
     * This constructor initializes the attributes
     * of a projectile such as its positions, damage, 
     * and speed. It also sets the projectile as active. 
     * 
     * 
     * @param row row position of  a projectile
     * @param col column position of a projectile
     * @param time the time of creation of the projectile
     * @param dmg damage it deals to another object
     * @param s speed of projectile 
     */
    Projectile(float row, float col, int time, int dmg, float s)
    {
        super(row, col, time);
        speed = s;
        damage = dmg;
        hasHit = false;
    }

    /**
     * This method is responsible for dealing damage
     * to its targeted zombie object. Once it hits its 
     * target, it deactivates the projectile, allowing it
     * to be removed. 
     * 
     * 
     * @param z target Zombie object
     */
    public void hit(Zombie z)
    {
        z.takeDamage(damage);
        //System.out.println("HP: " + z.getHealth());
        deactivate();

        //System.out.println("Projectile has hit zombie at (" + (z.getRow()+1) + ", " + (z.getCol()+1) + ")");
    }

    /**
     * This method is updates the movement
     * of a projectile after it is released. 
     *
     * @param currentTime the current time reference of the game
     */
    public void move(int currentTime)
    {
        float pos = getCol();

        if (currentTime - getInternal_Time() >= 1) {
            pos += (float) (1.0 / speed);
            setCol(pos);
            setInternal_Time(currentTime);
            //System.out.println("Projectile at (" + ((int)getRow()+1) + "," + ((int)getCol()+1) + ")");
        }
    }

    /**
     * This method handles the logic of the projectile
     * after a peashooter releases/shoots it. If it hits
     * the zombie object, it will be removed from the
     * list of projectiles. Otherwise, it will continue
     * moving. The current time reference is used to
     * know if the projectile should be updating or not.
     *
     * @param enemies list of zombie objects
     * @param currentTime the current time reference
     */
    public void update(ArrayList<Zombie> enemies, int currentTime)
    {
        int x;

        //loops through the list of enemies within the same row
        for(x = 0; x < enemies.size() && !hasHit; x++)
        {
            //if zombie is alive and vulnerable and projectile is within range of attack
            if(enemies.get(x).isAlive() && (enemies.get(x).getCol() - getCol()) < 0.5 && getRow() == enemies.get(x).getRow())
            {
                hit(enemies.get(x));
                hasHit = true;
            }

        }

        //if it has not hit any zombie yet, it will continue moving
        if(!hasHit)
        {
            move(currentTime);
        }
    }

    /** This method returns the damage output of a projectile
     *
     * @return the damage output of a projectile
     */
    public int getDamage() {
        return damage;
    }

    /** This method returns the hit status of the
     *  projectile.
     *
     * @return true if the object has hit a zombie,
     * false otherwise
     */
    public boolean getHitStatus() {
        return hasHit;
    }

    /** This method sets the hit status of the projectile.
     *
     * @param s the current status of the projectile
     */
    public void setHitStatus(boolean s) {
        hasHit = s;
    }

    /**value of damage dealt by projectile */
    private int damage;
    /**how fast the projectile moves once released */
    private float speed;
    private boolean hasHit;
}