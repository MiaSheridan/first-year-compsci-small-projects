

public class Bladesworn extends Warrior {

    public static double BASE_HEALTH;
    public static int BASE_COST;
    public static int WEAPON_TYPE = 3;
    public static int BASE_ATTACK_DAMAGE;

    public Bladesworn(Tile position) {

        super(position, BASE_HEALTH, WEAPON_TYPE, BASE_ATTACK_DAMAGE, BASE_COST);

    }

    public int takeAction() {
        // Get the current tile of the Bladesworn warrior
        Tile currentTile = this.getPosition();

        // Check if there is a monster on the same tile
        Monster monster = currentTile.getMonster();
        if (monster != null) {
            // Attack the first monster in the troop
            monster.takeDamage(this.getAttackDamage(), this.getWeaponType());
        } else {
            // No monster on the tile, so attempt to move toward the camp
            Tile nextTile = currentTile.towardTheCamp();
            if (nextTile != null && !nextTile.isCamp() && nextTile.getWarrior() == null) {
                // Move to the next tile
                currentTile.removeFighter(this); // Remove from current tile
                nextTile.addFighter(this);       // Add to the next tile
            }
        }

    
        return 0;
    }
    
    
}
