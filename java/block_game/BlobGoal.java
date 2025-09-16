

import java.awt.Color;

public class BlobGoal extends Goal{

	public BlobGoal(Color c) {
		super(c);
	}

	@Override
	public int score(Block board) {
		/*
		 * ADD YOUR CODE HERE
		 */
		int size = 0;
		Color [][] flattenedBoard = board.flatten();
		boolean [][] visited = new boolean [flattenedBoard.length][flattenedBoard[0].length];
		for (int i = 0; i < flattenedBoard.length; i++) {
			for (int j = 0; j < flattenedBoard[i].length; j++) {
				if (undiscoveredBlobSize(i,j,flattenedBoard,visited) > size)
					size = undiscoveredBlobSize(i,j,flattenedBoard,visited);
			}
		}

		return size;
		//return 0;
	}

	@Override
	public String description() {
		return "Create the largest connected blob of " + GameColors.colorToString(targetGoal) 
		+ " blocks, anywhere within the block";
	}


	public int undiscoveredBlobSize(int i, int j, Color[][] unitCells, boolean[][] visited) {
		/*
		 * ADD YOUR CODE HERE
		 */

		for (int a = 0; a < visited.length; a++) {
			for (int b = 0; b < visited[a].length; b++) {
				visited[a][b] = false;
			}
		}
		//visited[i][j] = true;
		Color currentCell = unitCells [i][j];
		if (currentCell != targetGoal) {
			return 0;
		}

		return blobForwardRecursion(unitCells,i,j,visited,0) + blobBackwardRecursion(unitCells, i, j, visited,0) + 1;
		//return 0;

	}
	private int blobUpwardRecursion(Color[][] unitCells, int i, int j, boolean[][] visited, int blobSize) {
		for (int a = 1; a < i; a++) {
			if (unitCells[i - a][j] == targetGoal && !visited[i - a][j]) {
				blobSize++;
				visited[i - a][j] = true;
				blobSize = blobForwardRecursion(unitCells,i - a, j, visited, blobSize) + blobBackwardRecursion(unitCells,i - a, j, visited, blobSize) - blobSize;
			}
			else {
				break;
			}
		}
		return blobSize;
	}
	private int blobDownwardRecursion (Color[][] unitCells, int i, int j, boolean[][] visited, int blobSize) {
		for (int a = 1; a < unitCells.length - i; a++) {
			if (unitCells[i + a][j] == targetGoal && !visited[i + a][j]) {
				blobSize++;
				visited[i + a][j] = true;
				blobSize = blobForwardRecursion(unitCells,i + a, j, visited, blobSize) + blobBackwardRecursion(unitCells,i + a, j, visited, blobSize) - blobSize;
			}
			else {
				break;
			}
		}
		return blobSize;
	}
	private int blobForwardRecursion(Color[][] unitCells, int i, int j, boolean[][] visited, int blobSize) {
		for (int a = 1; a < i; a++) {
			if (unitCells[i - a][j] == targetGoal && !visited[i - a][j]) {
				blobSize++;
				visited[i - a][j] = true;
			}
			else {
				break;
			}
		}
		for (int a = 1; a < unitCells.length - i; a++) {
			if (unitCells[i + a][j] == targetGoal && !visited[i + a][j]) {
				blobSize++;
				visited[i + a][j] = true;
			}
			else {
				break;
			}
		}
			for (int a = 1; a < unitCells.length - j; a++) {
				if (unitCells[i][j + a] == targetGoal && !visited[i][j + a]) {
					blobSize++;
					visited[i][j + a] = true;
					blobSize = blobUpwardRecursion(unitCells,i, j + a, visited, blobSize) + blobDownwardRecursion(unitCells,i, j + a, visited, blobSize) - blobSize;
				}
				else {
					break;
				}
			}
			return blobSize;
		}

	private int blobBackwardRecursion(Color[][] unitCells, int i, int j, boolean[][] visited, int blobSize) {

		for (int a = 1; a < j; a++) {
			if (unitCells[i][j - a] == targetGoal && !visited[i][j - a]) {
				blobSize++;
				visited[i][j-a] = true;
				blobSize = blobUpwardRecursion(unitCells,i, j - a, visited, blobSize) + blobDownwardRecursion(unitCells,i, j - a, visited, blobSize) - blobSize;
			}
			else {
				break;
			}
		}
		return blobSize;
	}




}
