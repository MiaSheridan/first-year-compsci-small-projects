

public class Lanceforged extends Warrior {

    public static double BASE_HEALTH;
    public static int BASE_COST;
    public static int WEAPON_TYPE=3;
    public static int BASE_ATTACK_DAMAGE;

    private int piercingPower;
    private int actionRange;

    public Lanceforged(Tile position, int piercingPower, int actionRange) {

        super(position, BASE_HEALTH, WEAPON_TYPE, BASE_ATTACK_DAMAGE, BASE_COST);

        // Initialize Lanceforged-specific fields
        this.piercingPower = piercingPower;
        this.actionRange = actionRange;
    
    }

    public int takeAction() {
        Tile currentTile = getPosition();

        // traverse the path toward the castle to find the nearest troop
        Tile nextTile = currentTile.towardTheCastle();


        int distance = 0; // Start counting distance from the first tile


        if (currentTile.getNumOfMonsters() == 0) {

            while (nextTile != null && distance <= actionRange) {
                // check if the tile has a valid troop (non-empty and not in a camp!!)
                if (nextTile.getNumOfMonsters() > 0 && !nextTile.isCamp()) {
                    // attack the troop on this tile
                    Monster[] monsters = nextTile.getMonsters();
                    int n = Math.min(piercingPower, monsters.length);
    
                    // deal damage to the first n monsters in the troop
                    for (int i = 0; i < n; i++) {
                        if (monsters[i] != null) {
                            // call takeDamage with attackDamage cast to double-type casting i guess
                            monsters[i].takeDamage((double) getAttackDamage(), getWeaponType());
                        }
                    }
    
                    // Exit after attacking the nearest troop
                    break;
                }
    
                // Move to the next tile toward the castle
                nextTile = nextTile.towardTheCastle();
                distance++; // Increment the distance


        }
    

        // Return 0 and do not modify the warrior's tile hopefully
        
    }
    return 0;
}}

    


    

