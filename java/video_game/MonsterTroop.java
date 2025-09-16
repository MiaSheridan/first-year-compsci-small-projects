

public class MonsterTroop {

    private Monster[] monsters;
    private int numOfMonsters;

    public MonsterTroop () {

        this.monsters = new Monster[1];
        this.numOfMonsters = 0;
    }
    public int sizeOfTroop() {

        return numOfMonsters;
    }

    public Monster[] getMonsters() {

        Monster[] troopCopy = new Monster[numOfMonsters];

        int i;

        for (i=0; i < numOfMonsters; i++) {
            
            troopCopy[i] = monsters[i];

        }
        return troopCopy;
    }

    public Monster getFirstMonster() {

        if( numOfMonsters == 0) {
            return null;
        }
        return monsters[0];
    }

    public void addMonster(Monster newMonster) {

        if ( numOfMonsters == monsters.length ) {

            expandCapacity();

        }

        monsters[numOfMonsters] = newMonster;
        numOfMonsters++;

    }
    
    private void expandCapacity(){

        int newSize = monsters.length *2 ; // doubling the size

        Monster[] newArray = new Monster[newSize];


        int i;
        for (i=0; i < numOfMonsters ; i ++) {

            newArray[i] = monsters[i];
        }

        monsters = newArray; //replacing old array with new LARGER thingie

    }

    public boolean removeMonster(Monster desiredMonster) {

        int i;

        for (i =0; i < numOfMonsters; i++) {

            if (monsters[i] == desiredMonster) {

                shiftLeft(i);
                numOfMonsters --;
                return true;
            }
        }
        return false; //Monster not found

    }

    private void shiftLeft(int startIndex) {

        int i;

        for (i=startIndex; i < numOfMonsters -1; i++) {

            monsters[i] = monsters[i+1];
        }
        monsters[numOfMonsters -1] = null; //Remove duplicate references
    }


}


