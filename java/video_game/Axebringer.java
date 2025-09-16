

public class Axebringer extends Warrior {

    public static double BASE_HEALTH;
    public static int BASE_COST;
    public static int WEAPON_TYPE =2;
    public static int BASE_ATTACK_DAMAGE;

    public Axebringer(Tile position) {

        super(position, BASE_HEALTH, BASE_COST, WEAPON_TYPE, BASE_ATTACK_DAMAGE);
    }

    //Override???

    public int takeAction() {
        // get the current tile of the axebr
        Tile currentTile = this.getPosition();

        // check if there is monster on same tile
        Monster monster = currentTile.getMonster();
        if (monster != null) {
            // attack the first monster in the troop
            monster.takeDamage(this.getAttackDamage(), this.getWeaponType());
        } else {
            // no monster on the tile, so check the immediate next tile toward the camp
            Tile nextTile = currentTile.towardTheCamp();
            if (nextTile != null && !nextTile.isCamp()) {
                // check if there is a monster on the next tile
                Monster nextTileMonster = nextTile.getMonster();
                if (nextTileMonster != null) {
                    // throw the axe to attack the first monster in the next tile's troop
                    nextTileMonster.takeDamage(this.getAttackDamage(), this.getWeaponType());
                }
            }
        }
        return 0;
    }






    
}
