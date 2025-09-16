

public class Tile {

    private boolean isCastle;
    private boolean isCamp;
    private boolean onThePath;
    private Tile towardTheCastle;
    private Tile towardTheCamp;
    private Warrior warrior;
    private MonsterTroop troop;

    public Tile() {
        this.isCastle = false;
        this.isCamp = false;
        this.onThePath = false;
        this.towardTheCamp = null;
        this.towardTheCastle = null;
        this.warrior = null;
        this.troop = new MonsterTroop(); // Initialize an empty troop

    }

    public Tile( boolean isCastle, boolean isCamp, boolean onThePath, Tile towardTheCastle,Tile towardTheCamp, Warrior warrior, MonsterTroop troop ) {

        this.isCastle = isCastle;
        this.isCamp = isCamp;
        this.onThePath = onThePath;
        this.towardTheCastle = towardTheCastle;
        this.towardTheCamp = towardTheCamp;
        this.warrior = warrior;
        this.troop = troop;

    }

    public boolean isCastle() {

        return this.isCastle;
    }

    public boolean isCamp() {

        return this.isCamp;
    }

    public void buildCastle() {
        this.isCastle = true;

    }

    public void buildCamp() {

        this.isCamp= true;
    }

    public boolean isOnThePath() {
        return this.onThePath;

    }

    public Tile towardTheCastle() {

        if ( !this.onThePath || this.isCastle) {

            return null;
       }

       return this.towardTheCastle;
    }

    public Tile towardTheCamp() {

        if( !this.onThePath || this.isCamp){

            return null;
        }
        return this.towardTheCamp;
    }

    public void createPath(Tile towardTheCastle, Tile towardTheCamp) {

        if (this.isCastle && towardTheCastle != null) {

            throw new IllegalArgumentException("Error: Castle tile cannot have a path toward the castle ");

        }

        if (this.isCamp && towardTheCamp != null) {
            throw new IllegalArgumentException("Camp tile cannot have a path toward the camp.");
        }

        // Check if the inputs are invalid for non-castle/non-camp tiles
        if (!this.isCastle && !this.isCamp) {
            if (towardTheCastle == null || towardTheCamp == null) {
                throw new IllegalArgumentException("Non-castle/non-camp tiles must have valid paths toward both the castle and the camp.");
            }
        }

        //updating the path fields
        this.towardTheCastle = towardTheCastle;
        this.towardTheCamp = towardTheCamp;
        this.onThePath = true; // Mark this tile as part of the path

    }

    public int getNumOfMonsters() {

        return this.troop.sizeOfTroop();
    }

    public Warrior getWarrior() {

        return this.warrior;
    }

    public Monster getMonster() {

        return this.troop.getFirstMonster();
    }

    public Monster[] getMonsters() {

        return this.troop.getMonsters();

    }

    //add Fighter method-using polymorphsism or wtv

    public boolean addFighter(Fighter fighter) {
        if (fighter instanceof Warrior) {
            Warrior warrior = (Warrior) fighter;
            if (this.warrior != null || this.isCamp) {
                return false; // Cannot add a warrior if there's already one or if it's the camp tile
            }
            this.warrior = warrior;
            warrior.setPosition(this); // Update the warrior's position
            return true;
        } else if (fighter instanceof Monster) {
            Monster monster = (Monster) fighter;
            if (!this.onThePath && !this.isCastle && !this.isCamp) {
                return false; // Monsters can only be added to tiles on the path, castle, or camp
            }
            this.troop.addMonster(monster);
            monster.setPosition(this); // Update the monster's position
            return true;
        }
        return false; // If the fighter is neither a Warrior nor a Monster
    }

    //method to remove Figther also using isntanceof for polymorpshism 
    public boolean removeFighter(Fighter fighter) {
        if (fighter instanceof Warrior) {
            Warrior warrior = (Warrior) fighter;
            if (this.warrior == warrior) {
                this.warrior = null; // Remove the warrior from the tile
                warrior.setPosition(null); // Update the warrior's position
                return true;
            }
        } else if (fighter instanceof Monster) {
            Monster monster = (Monster) fighter;
            boolean removed = this.troop.removeMonster(monster);
            if (removed) {
                monster.setPosition(null); // Update the monster's position
            }
            return removed;
        }
        return false; // Fighter not found on the tile
    }
   
}
