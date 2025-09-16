public abstract class Fighter {

    private Tile position;
    private double health;
    private int weaponType;
    private int attackDamage;

    public Fighter(Tile position, double health, int weaponType, int attackDamage) {


        //Chek if position is null
        if (position == null) {

            throw new IllegalArgumentException("Position cannot be null."); // Should I write a mesage here or nah??
        }

        //add fighter to the tile

        if (!position.addFighter(this)) {

            throw new IllegalArgumentException("Fighter could not be added to the specified tile.");

        }

        //If fighter is cool added then initializing fields

        this.position = position;
        this.health = health;
        this.weaponType = weaponType;
        this.attackDamage = attackDamage;



    }

    public final Tile getPosition() {

        return position;

    }

    public final double getHealth() {
        
        return health;
    }

    public final int getWeaponType() {

        return weaponType;
    
    }

    public final int getAttackDamage() {

        return attackDamage;
    }

    public void setPosition( Tile position) {

        this.position = position;
    }

    public double takeDamage(double rawDamage, int attackerWeaponType) {

        double damageMultiplier;

        if (attackerWeaponType > this.weaponType) {

            damageMultiplier = 1.5;
        } else if (attackerWeaponType < this.weaponType) {

            damageMultiplier = 0.5;
        } else {

            damageMultiplier = 1.0;
        }

        double actualDamage = rawDamage * damageMultiplier;

        this.health -= actualDamage;

        // Check if the fighter is defeated (health <= 0)
        if (this.health <= 0) {
            // Remove the fighter from its tile
            if (this.position != null) {
                this.position.removeFighter(this);
            }


        }

        return actualDamage;


    }

    public abstract int takeAction();

    @Override
    public boolean equals(Object obj) {
        // Check if the object is the same instance
        if (this == obj) {
            return true;
        }
    
        // Check if the object is null or of a different class
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
    
        // Cast the object to Fighter
        Fighter other = (Fighter) obj;
    
        // Compare position (using reference eq.)
        if (this.position != other.position) {
            return false;
        }
    
        // Compare health (allowing for small difference)
        if (Math.abs(this.health - other.health) > 0.001) {
            return false;
        }
    
        // If all checks pass,
        return true;
    }


}

