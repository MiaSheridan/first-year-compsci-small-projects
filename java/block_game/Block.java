

import java.util.ArrayList;
import java.util.Random;
import java.awt.Color;

public class Block {
	private int xCoord;
	private int yCoord;
	private int size; // height/width of the square
	private int level; // the root (outer most block) is at level 0
	private int maxDepth; 
	private Color color;

	private Block[] children; // {UR, UL, LL, LR}

	public static Random gen = new Random(); 


	/*
	 * These two constructors are here for testing purposes. 
	 */
	public Block() {}

	public Block(int x, int y, int size, int lvl, int  maxD, Color c, Block[] subBlocks) {
		this.xCoord=x;
		this.yCoord=y;
		this.size=size;
		this.level=lvl;
		this.maxDepth = maxD;
		this.color=c;
		this.children = subBlocks;
	}



	/*
	 * Creates a random block given its level and a max depth. 
	 * 
	 * xCoord, yCoord, size, and highlighted should not be initialized
	 * (i.e. they will all be initialized by default)
	 */
	public Block(int lvl, int maxDepth) {
		/*
		 * ADD YOUR CODE HERE
		 */
		this.level = lvl;
		this.maxDepth = maxDepth;
		
		// Only consider subdividing if we haven't reached max depth
		if (lvl < maxDepth) {
			double randomvalue = gen.nextDouble();
			if (randomvalue < Math.exp(-0.25 * lvl)) {
				// Subdivide the block
				children = new Block[4];
				for (int i = 0; i < 4; i++) {
					children[i] = new Block(lvl + 1, maxDepth);
				}
				color = null; // Divided blocks have no color
			} else {
				// Don't subdivide - assign random color
				int colorindex = gen.nextInt(GameColors.BLOCK_COLORS.length);

				color = GameColors.BLOCK_COLORS[colorindex];
				children = new Block[0];
			}
		} else {
			// At max depth - must be a leaf
			int colorindex = gen.nextInt(GameColors.BLOCK_COLORS.length);
			color = GameColors.BLOCK_COLORS[colorindex];
			children = new Block[0];
		}


		
	}

	
	// These get methods have been provided. Do NOT modify them. 
	public int getXCoord() { return this.xCoord; }
	
	public int getYCoord() { return this.yCoord; }
	
	public int getMaxDepth() { return this.maxDepth; }

	public int getLevel() { return this.level; }
	
	public int getSize() { return this.size; }
	
	public Color getColor() { return this.color; }
	
	public Block[] getChildren() {return this.children; }


	/*
	 * Updates size and position for the block and all of its sub-blocks, while
	 * ensuring consistency between the attributes and the relationship of the 
	 * blocks. 
	 * 
	 *  The size is the height and width of the block. (xCoord, yCoord) are the 
	 *  coordinates of the top left corner of the block. 
	 */
	public void updateSizeAndPosition (int size, int xCoord, int yCoord) {
		/*
		 * ADD YOUR CODE HERE
		 */
	    if (size <= 0 || (size % (int) Math.pow(2, maxDepth - level) != 0)) {
			//should i just do (level != maxDepth && size % 2 != 0) || size < 0)
			throw new IllegalArgumentException("Input for size is invalid");
		}
		
		this.size = size;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
		
		if (children.length > 0) {
			int childSize = size / 2;
			// update children positions (UR, UL, LL, LR order)
			children[0].updateSizeAndPosition(childSize, xCoord + childSize, yCoord); // UR
			children[1].updateSizeAndPosition(childSize, xCoord, yCoord);             // UL
			children[2].updateSizeAndPosition(childSize, xCoord, yCoord + childSize); // LL
			children[3].updateSizeAndPosition(childSize, xCoord + childSize, yCoord + childSize); // LR
		}	
		
	}


	/*
	 * Returns a List of blocks to be drawn to get a graphical representation of this block.
	 * 
	 * This includes, for each undivided Block:
	 * - one BlockToDraw in the color of the block
	 * - another one in the FRAME_COLOR and stroke thickness 3
	 * 
	 * Note that a stroke thickness equal to 0 indicates that the block should be filled with its color.
	 *  
	 * The order in which the blocks to draw appear in the list does NOT matter.
	 */
	public ArrayList<BlockToDraw> getBlocksToDraw() {
		/*
		 * ADD YOUR CODE HERE
		
		 */
		ArrayList<BlockToDraw> blocks = new ArrayList<>();
		getBlocksRecursion(blocks);
  		return blocks;

		/*
		if (children.length == 0) {
			// Add filled block
			blocks.add(new BlockToDraw(color, xCoord, yCoord, size, 0));
			// Add frame
			blocks.add(new BlockToDraw(GameColors.FRAME_COLOR, xCoord, yCoord, size, 3));
		} else {
			for (Block child : children) {
				blocks.addAll(child.getBlocksToDraw());
			}
		}
		
		return blocks;
		*/
	
    
		//return null;
	}
	private void getBlocksRecursion(ArrayList<BlockToDraw> listOfBlocks) {
		if (level > maxDepth) {
		 return;
		}
		if (children.length == 0) {
		 BlockToDraw blockToDraw1 = new BlockToDraw(this.color, this.xCoord, this.yCoord, this.size, 0);
		 BlockToDraw blockToDraw2 = new BlockToDraw(GameColors.FRAME_COLOR, this.xCoord, this.yCoord, this.size, 3);
		 listOfBlocks.add(blockToDraw1);
		 listOfBlocks.add(blockToDraw2);
		}
		for (int i = 0; i < children.length; i++) {
		 Block block = children[i];
		 if (block.children.length == 0) {
		  BlockToDraw blockToDraw1 = new BlockToDraw(block.color, block.xCoord, block.yCoord, block.size, 0);
		  BlockToDraw blockToDraw2 = new BlockToDraw(GameColors.FRAME_COLOR, block.xCoord, block.yCoord, block.size, 3);
		  listOfBlocks.add(blockToDraw1);
		  listOfBlocks.add(blockToDraw2);
		 }
		 else {
		  children[i].getBlocksRecursion(listOfBlocks);
		 }
		}
	   }



	/*
	 * This method is provided and you should NOT modify it. 
	 */
	public BlockToDraw getHighlightedFrame() {
		return new BlockToDraw(GameColors.HIGHLIGHT_COLOR, this.xCoord, this.yCoord, this.size, 5);
	}



	/*
	 * Return the Block within this Block that includes the given location
	 * and is at the given level. If the level specified is lower than 
	 * the lowest block at the specified location, then return the block 
	 * at the location with the closest level value.
	 * 
	 * The location is specified by its (x, y) coordinates. The lvl indicates 
	 * the level of the desired Block. Note that if a Block includes the location
	 * (x, y), and that Block is subdivided, then one of its sub-Blocks will 
	 * contain the location (x, y) too. This is why we need lvl to identify 
	 * which Block should be returned. 
	 * 
	 * Input validation: 
	 * - this.level <= lvl <= maxDepth (if not throw exception)
	 * - if (x,y) is not within this Block, return null.
	 */
	public Block getSelectedBlock(int x, int y, int lvl) {
		/*
		 * ADD YOUR CODE HERE
		 */
		if (lvl < level || lvl > maxDepth) {
			throw new IllegalArgumentException("The level provided is invalid.");
		   }
		   return getSelectedRecursion(x, y, lvl);
	}

	private Block getSelectedRecursion(int x, int y, int lvl) {
		if (lvl == 0) {
		 return this;
		}
		for (int i = 0; i < children.length; i++) {
		 Block block = children[i];
		 if ((x >= block.xCoord && x < block.xCoord + block.size) && (y >= block.yCoord && y < block.yCoord + block.size)) {
		  if (block.level == lvl) { //if level is the same, return
		   return block;
		  } else if (block.level < lvl && block.children.length != 0) { //if lvl is bigger, look into children
		   return children[i].getSelectedRecursion(x, y, lvl);
		  } else { //if lvl is bigger and no children, just return block
		   return block;
		  }
		 }
		 children[i].getSelectedRecursion(x, y, lvl);
		}
		return null;
	   }


	/*
	 * Reflect this Block and all its descendants. 
	 * If input is 0, reflect along the main diagonal (x=y). 
	 * If 1, reflect on the anti-diagonal (x=-y). 
	 * If this Block has no children, do nothing.  
	 */
	public void reflect(int direction) {
		/*
		 * ADD YOUR CODE HERE
		 */
		if ((direction != 0 && direction != 1) || size == 0) {
			throw new IllegalArgumentException("The input should be 0 or 1.");
		   }
		   if (children.length != 0 ) {
		 
			Block block1 = children[0];
			Block block2 = children[1];
			Block block3 = children[2];
			Block block4 = children[3];
		 
			if (direction == 0) {
		 
			 block1.updateSizeAndPosition(block1.size, block1.xCoord, block1.yCoord + block1.size);
			 if (block1.children.length != 0) {
			  block1.reflect(direction);
			 }
			 block2.updateSizeAndPosition(block2.size, block2.xCoord, block2.yCoord + block2.size);
			 if (block2.children.length != 0) {
			  block2.reflect(direction);
			 }
			 block3.updateSizeAndPosition(block3.size, block3.xCoord, block3.yCoord - block3.size);
			 if (block3.children.length != 0) {
			  block3.reflect(direction);
			 }
			 block4.updateSizeAndPosition(block4.size, block4.xCoord, block4.yCoord - block4.size);
			 if (block4.children.length != 0) {
			  block3.reflect(direction);
			 }
			 children[0] = block4;
			 children[1] = block3;
			 children[2] = block2;
			 children[3] = block1;
			} else {
			 block2.updateSizeAndPosition(block2.size, block2.xCoord + block2.size, block2.yCoord);
			 if (block2.children.length != 0) {
			  block2.reflect(direction);
			 }
			 block3.updateSizeAndPosition(block3.size, block3.xCoord + block3.size, block3.yCoord);
			 if (block3.children.length != 0) {
			  block3.reflect(direction);
			 }
			 block1.updateSizeAndPosition(block1.size, block1.xCoord - block1.size, block1.yCoord);
			 if (block1.children.length != 0) {
			  block1.reflect(direction);
			 }
			 block4.updateSizeAndPosition(block4.size, block4.xCoord - block4.size, block4.yCoord);
			 if (block4.children.length != 0) {
			  block4.reflect(direction);
			 }
			 children[0] = block2;
			 children[1] = block1;
			 children[2] = block4;
			 children[3] = block3;
			}
		   }

		

		 /*
		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException("Direction must be 0 or 1");
		}
		
		if (children.length == 0) {
			return;
		}
		
		// Reflect children array based on direction
		Block[] reflectedChildren = new Block[4];
		if (direction == 0) { // main diagonal (x=y)
			reflectedChildren[0] = children[0];
			reflectedChildren[1] = children[3];
			reflectedChildren[2] = children[2];
			reflectedChildren[3] = children[1];
		} else { // anti-diagonal (x=-y)
			reflectedChildren[0] = children[2];
			reflectedChildren[1] = children[1];
			reflectedChildren[2] = children[0];
			reflectedChildren[3] = children[3];
		}
		
		this.children = reflectedChildren;
		
		// Update positions of all children
		updateSizeAndPosition(size, xCoord, yCoord);
		
		// Reflect all children
		for (Block child : children) {
			child.reflect(direction);
		}
		*/

		
	}



	/*
	 * Rotate this Block and all its descendants. 
	 * If the input is 0, rotate clockwise. If 1, rotate 
	 * counterclockwise. If this Block has no children, do nothing.
	 */
	public void rotate(int direction) {
		/*
		 * ADD YOUR CODE HERE
		 */
		if ((direction != 0 && direction != 1) || size == 0) {
			throw new IllegalArgumentException("The input should be 0 or 1.");
		   }
		   if (children.length != 0) {
		 
			Block block1 = children[0];
			Block block2 = children[1];
			Block block3 = children[2];
			Block block4 = children[3];
		 
			if (direction == 0) {
		 
			 block1.updateSizeAndPosition(block1.size, block1.xCoord - block1.size, block1.yCoord);
			 if (block1.children.length != 0) {
			  block1.rotate(direction);
			 }
			 block2.updateSizeAndPosition(block2.size, block2.xCoord, block2.yCoord + block2.size);
			 if (block2.children.length != 0) {
			  block2.rotate(direction);
			 }
			 block3.updateSizeAndPosition(block3.size, block3.xCoord + block3.size, block3.yCoord);
			 if (block3.children.length != 0) {
			  block3.rotate(direction);
			 }
			 block4.updateSizeAndPosition(block4.size, block4.xCoord, block4.yCoord - block4.size);
			 if (block4.children.length != 0) {
			  block3.rotate(direction);
			 }
			 children[0] = block4;
			 children[1] = block1;
			 children[2] = block2;
			 children[3] = block3;
			} else {
			 block1.updateSizeAndPosition(block1.size, block1.xCoord, block1.yCoord + block1.size);
			 if (block1.children.length != 0) {
			  block1.rotate(direction);
			 }
			 block2.updateSizeAndPosition(block2.size, block2.xCoord + block2.size, block2.yCoord);
			 if (block2.children.length != 0) {
			  block2.rotate(direction);
			 }
			 block3.updateSizeAndPosition(block3.size, block3.xCoord, block3.yCoord - block3.size);
			 if (block3.children.length != 0) {
			  block3.rotate(direction);
			 }
			 block4.updateSizeAndPosition(block4.size, block4.xCoord - block4.size, block4.yCoord);
			 if (block4.children.length != 0) {
			  block4.rotate(direction);
			 }
			 children[0] = block2;
			 children[1] = block3;
			 children[2] = block4;
			 children[3] = block1;
			}
		   }

		

		/* 
		if (direction != 0 && direction != 1) {
			throw new IllegalArgumentException("Direction must be 0 or 1");
		}
		
		if (children.length == 0) {
			return;
		}
		
		// Rotate children array based on direction
		Block[] rotatedChildren = new Block[4];
		if (direction == 0) { // clockwise
			rotatedChildren[0] = children[3];
			rotatedChildren[1] = children[0];
			rotatedChildren[2] = children[1];
			rotatedChildren[3] = children[2];
		} else { // counter-clockwise
			rotatedChildren[0] = children[1];
			rotatedChildren[1] = children[2];
			rotatedChildren[2] = children[3];
			rotatedChildren[3] = children[0];
		}
		
		this.children = rotatedChildren;
		
		// Update positions of all children
		updateSizeAndPosition(size, xCoord, yCoord);
		
		// Rotate all children
		for (Block child : children) {
			child.rotate(direction);
		}
		*/


	}



	/*
	 * Smash this Block.
	 * 
	 * If this Block can be smashed,
	 * randomly generate four new children Blocks for it.  
	 * (If it already had children Blocks, discard them.)
	 * Ensure that the invariants of the Blocks remain satisfied.
	 * 
	 * A Block can be smashed iff it is not the top-level Block 
	 * and it is not already at the level of the maximum depth.
	 * 
	 * Return True if this Block was smashed and False otherwise.
	 * 
	 */
	public boolean smash() {
		/*
		 * ADD YOUR CODE HERE
		 */
	    // Can't smash top-level block or blocks at max depth
		if (level == 0 || level >= maxDepth) {
			return false;
		}
		
		children = new Block[4];

		Block newBlock = new Block(level + 1, maxDepth);
		newBlock.updateSizeAndPosition(size/2, size/2, 0);
		children[0] = newBlock;
		Block newBlock1 = new Block(level + 1, maxDepth);
		newBlock1.updateSizeAndPosition(size/2, 0, 0);
		children[1] = newBlock1;
		Block newBlock2 = new Block(level + 1, maxDepth);
		newBlock2.updateSizeAndPosition(size/2, 0, size/2);
		children[2] = newBlock2;
		Block newBlock3 = new Block(level + 1, maxDepth);
		newBlock3.updateSizeAndPosition(size/2, size/2, size/2);
		children[3] = newBlock3;
	
	
	 	return true;
	
		//return false;
	}

	
	/*
	 * Condense this Block.
	 * 
	 * If this Block can be condensed,
	 * then remove its subdivisions and set its color to 
	 * the most frequent one
	 * 
	 * A Block can be condensed iff it is not the top-level Block 
	 * and it is currently has four children. 
	 * 
	 * Return True if this Block was condensed and False otherwise.
	 * 
	 */
	public boolean condense() {
		/*
		 * ADD YOUR CODE HERE
		 */
	    // Can't condense top-level block or blocks without exactly 4 children
		if (level == 0 || children.length != 4) {
			return false;
		}
		
		// Count colors in children
		int[] colorCounts = new int[GameColors.BLOCK_COLORS.length];
		for (Block child : children) {
			if (child.color != null) {
				for (int i = 0; i < GameColors.BLOCK_COLORS.length; i++) {
					if (child.color.equals(GameColors.BLOCK_COLORS[i])) {
						colorCounts[i]++;
						break;
					}
				}
			}
		}
		
		// Find most frequent color
		int maxCount = 0;
		Color mostFrequentColor = null;
		for (int i = 0; i < colorCounts.length; i++) {
			if (colorCounts[i] > maxCount) {
				maxCount = colorCounts[i];
				mostFrequentColor = GameColors.BLOCK_COLORS[i];
			}
		}
		
		// Only condense if there's a clear most frequent color
		if (maxCount > 0) {
			this.color = mostFrequentColor;
			this.children = new Block[0];
			return true;
		}
		
		return false;
		//return false;
	}

	/*
	 * Return a two-dimensional array representing this Block as rows and columns of unit cells.
	 * 
	 * Return and array arr where, arr[i] represents the unit cells in row i, 
	 * arr[i][j] is the color of unit cell in row i and column j.
	 * 
	 * arr[0][0] is the color of the unit cell in the upper left corner of this Block.
	 */
	public Color[][] flatten() {
		/*
		 * ADD YOUR CODE HERE
		 */
		int unitCell = 1; //when maxDepth is 0
		for (int z = 0; z < maxDepth; z++) {
		 unitCell = unitCell * 2; //for every increase in maxDepth, unitCell doubles to cover a side of big blob
		}
		Color[][] arr = new Color[unitCell][unitCell];
	  
	  
		for (int i = 0; i < unitCell; i++) {
		 for (int j = 0; j < unitCell; j++) {
		   arr[i][j] = flattenRecursion(size / unitCell * j, size / unitCell * i, maxDepth).color;
		 }
		}
		return arr;
		

		 /*
		int unitSize = size / (int)Math.pow(2, maxDepth - level);
		int gridSize = (int)Math.pow(2, maxDepth - level);
		Color[][] grid = new Color[gridSize][gridSize];
		
		if (children.length == 0) {
			// Fill entire grid with this block's color
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					grid[i][j] = color;
				}
			}
		} else {
			// Recursively flatten children and combine
			Color[][][] childGrids = new Color[4][][];
			for (int i = 0; i < 4; i++) {
				childGrids[i] = children[i].flatten();
			}
			
			int halfSize = gridSize / 2;
			for (int i = 0; i < gridSize; i++) {
				for (int j = 0; j < gridSize; j++) {
					if (i < halfSize) {
						if (j < halfSize) {
							grid[i][j] = childGrids[1][i][j]; // UL
						} else {
							grid[i][j] = childGrids[0][i][j - halfSize]; // UR
						}
					} else {
						if (j < halfSize) {
							grid[i][j] = childGrids[2][i - halfSize][j]; // LL
						} else {
							grid[i][j] = childGrids[3][i - halfSize][j - halfSize]; // LR
						}
					}
				}
			}
		}
		
		return grid;
		//return null;
		*/
	}
	private Block flattenRecursion(int x, int y, int lvl) {
		if ((x >= this.xCoord && x < this.xCoord + this.size) && (y >= this.yCoord && y < this.yCoord + this.size) && children.length == 0) {
		 return this;
		}
		for (int i = 0; i < children.length; i++) {
		 Block block = children[i];
		 if ((x >= block.xCoord && x < block.xCoord + block.size) && (y >= block.yCoord && y < block.yCoord + block.size)) {
		  if (block.level == lvl) { //if level is the same, return
		   return block;
		  } else if (block.level < lvl && block.children.length != 0) { //if level is less than maxDepth, look into children
		   return children[i].flattenRecursion(x, y, lvl);
		  } else { //if lvl is bigger and no children, just return block
		   return block;
		  }
		 }
		 children[i].flattenRecursion(x, y, lvl);
		}
		return this;
	   }






	/*
	 * The next 5 methods are needed to get a text representation of a block. 
	 * You can use them for debugging. You can modify these methods if you wish.
	 */
	public String toString() {
		return String.format("pos=(%d,%d), size=%d, level=%d"
				, this.xCoord, this.yCoord, this.size, this.level);
	}

	public void printBlock() {
		this.printBlockIndented(0);
	}

	private void printBlockIndented(int indentation) {
		String indent = "";
		for (int i=0; i<indentation; i++) {
			indent += "\t";
		}

		if (this.children.length == 0) {
			// it's a leaf. Print the color!
			String colorInfo = GameColors.colorToString(this.color) + ", ";
			System.out.println(indent + colorInfo + this);   
		} else {
			System.out.println(indent + this);
			for (Block b : this.children)
				b.printBlockIndented(indentation + 1);
		}
	}

	private static void coloredPrint(String message, Color color) {
		System.out.print(GameColors.colorToANSIColor(color));
		System.out.print(message);
		System.out.print(GameColors.colorToANSIColor(Color.WHITE));
	}

	public void printColoredBlock(){
		Color[][] colorArray = this.flatten();
		for (Color[] colors : colorArray) {
			for (Color value : colors) {
				String colorName = GameColors.colorToString(value).toUpperCase();
				if(colorName.length() == 0){
					colorName = "\u2588";
				}else{
					colorName = colorName.substring(0, 1);
				}
				coloredPrint(colorName, value);
			}
			System.out.println();
		}
	}
	
}
