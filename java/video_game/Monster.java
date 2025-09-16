

public class Monster extends Fighter {

    private int rageLevel=0;
    public static int BERSERK_THRESHOLD;


    public Monster(Tile position, double health, int weaponType, int attackDamage) {

        super(position, health, weaponType, attackDamage);
    }

    @Override
    public double takeDamage(double rawDamage, int attackerWeaponType) {
        // Call the superclass's takeDamage method to handle basic damage calculation
        double actualDamage = super.takeDamage(rawDamage, attackerWeaponType);

        // Calculate rage gain if the attacker's weapon is superior
        if (attackerWeaponType > this.getWeaponType()) {
            int rageGain = attackerWeaponType - this.getWeaponType();
            this.rageLevel += rageGain; // Increase rage level
        }

        // Return the actual damage dealt (as returned by the superclass)
        return actualDamage;
    }


    /*public int takeAction() {
        // Get the current tile of the monster
        Tile currentTile = this.getPosition();

        // Check if there is a warrior on the same tile
        Warrior warrior = currentTile.getWarrior();
        if (warrior != null) {
            // Attack the warrior
            warrior.takeDamage(this.getAttackDamage(), this.getWeaponType());

            // Move the monster to the back of the troop
            currentTile.removeFighter(this); // Remove the monster from the current tile
            currentTile.addFighter(this);    // Add it back to the end of the troop
        } else {
            // No warrior on the tile, so move toward the castle
            Tile nextTile = currentTile.towardTheCastle();
            if (nextTile != null) {
                // Move the monster to the next tile
                currentTile.removeFighter(this); // Remove from current tile
                nextTile.addFighter(this);       // Add to the next tile
            }
        }

        return 0;
    }/* */

    public int takeAction() {
        // Check if the monster is in berserk mode
        boolean isBerserk = this.rageLevel >= BERSERK_THRESHOLD;

        // Perform actions based on whether the monster is berserk
        if (isBerserk) {
            // Perform two actions
            performAction(); // First action
            performAction(); // Second action

            // Reset rage level after berserk mode
            this.rageLevel = 0;
        } else {
            // Perform one action
            performAction();
        }

        return 0;
    }

    private void performAction() {
        // Get the current tile of the monster
        Tile currentTile = this.getPosition();

        // Check if there is a warrior on the same tile
        Warrior warrior = currentTile.getWarrior();
        if (warrior != null) {
            // Attack the warrior
            warrior.takeDamage(this.getAttackDamage(), this.getWeaponType());

            // Move the monster to the back of the troop
            currentTile.removeFighter(this); // Remove the monster from the current tile
            currentTile.addFighter(this);    // Add it back to the end of the troop
        } else {
            // No warrior on the tile, so move toward the castle
            Tile nextTile = currentTile.towardTheCastle();
            if (nextTile != null) {
                // Move the monster to the next tile
                currentTile.removeFighter(this); // Remove from current tile
                nextTile.addFighter(this);       // Add to the next tile
            }
        }
    }

    

    @Override
    public boolean equals(Object object) {
        // First, check if the superclass considers them equal
        if (!super.equals(object)) {
            return false;
        }

        // Now, check Monster-specific fields (attackDamage)
        Monster other = (Monster) object;
        return this.getAttackDamage() == other.getAttackDamage();
    }
    

}

