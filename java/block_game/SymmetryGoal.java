

import java.awt.Color;

public class SymmetryGoal extends Goal {
	int direction;

	public SymmetryGoal(int i) {
		super(null);
		this.direction = i; // symmetry along the x-axis (0) or the y-axis (1)
	}

	@Override
	public int score(Block board) {
		/*
		 * ADD YOUR CODE HERE
		 */
        if (board == null) {
            return 0;
        }

        // Flatten the board to a grid of unit cells
        Color[][] grid = board.flatten();
        int score = 0;

        // Check each unit cell for symmetry with its counterpart
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int mirroredI, mirroredJ;
                
                if (direction == 0) { // Horizontal symmetry (x-axis)
                    mirroredI = i;
                    mirroredJ = grid[i].length - 1 - j;
                } else { // Vertical symmetry (y-axis)
                    mirroredI = grid.length - 1 - i;
                    mirroredJ = j;
                }
                
                // Check if the mirrored cell exists and has the same color
                if (mirroredI >= 0 && mirroredI < grid.length && 
                    mirroredJ >= 0 && mirroredJ < grid[mirroredI].length) {
                    if (colorsMatch(grid[i][j], grid[mirroredI][mirroredJ])) {
                        score++;
                    }
                }
            }
        }
        
        return score;
		//return 0;
	}

	private boolean colorsMatch(Color a, Color b) {
        if (a == null && b == null) return true;
        if (a == null || b == null) return false;
        return a.equals(b);
    }



	@Override
	public String description() {
	    return "Make the board as symmetrical as possible along the " 
	           + (direction == 0 ? "horizontal (x-axis)" : "vertical (y-axis)") 
	           + " axis.";
	}
	
}
