

public abstract class Warrior extends Fighter {

    private int requiredSkillPoints; //ok not sure about this Castle field
    public static double CASTLE_DMG_REDUCTION=0.5; //50% reduction lets say but idkkk

//Once Im done testing it, I need take out the value 0.5

    public Warrior(Tile position, double health, int weaponType, int attackDamage, int requiredSkillPoints) {

        super(position, health, weaponType, attackDamage);
        this.requiredSkillPoints = requiredSkillPoints;
    }

    public int getTrainingCost() {

        return requiredSkillPoints;
    }
    
    @Override
    public double takeDamage(double rawDamage, int attackerWeaponType) {

        if (this.getPosition() != null && this.getPosition().isCastle()) {

            rawDamage *= (1- CASTLE_DMG_REDUCTION);
        }
        // this calls superclass takeDamage method to apply damage
        return super.takeDamage(rawDamage, attackerWeaponType);
    }



    
}
