import java.awt.*;
import java.util.Random;

/**
 * Map: This class should store information about the map and
 * get information stored in specific tiles.
 */

public class Map {
    private int width;
    private int height;
    private Tile [][] tileMatrix;
	private int [][] roomMatrix;
	private Random random;
	/**
     * Construct the map by initializing the tile matrix of size width * height
     * that stores all information on the map.
     * @param width width of map to be constructed
     * @param height ditto, but for height
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tileMatrix = new Tile[width][height];
		roomMatrix = new int[width][height];

        // Add empty tile elements to the entire tile matrix.
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j ++){
                tileMatrix[i][j] = new Tile(false);
            }
        }
        random = new Random();
    }

    /**
     * Return the the tile object located at (x, y) in the tile matrix.
     * @param x x-position of tile in matrix
	 *          origin of matrix is in the centre of the map.
     * @param y y-position of tile in the matrix.
     * @return Tile at coordinates
     * @throws IndexOutOfBoundsException if index does lies outside the map.
     */
    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        if(!isValidTile(x,y)) {
            throw new IndexOutOfBoundsException("Tile is out of bounds.");
        }
        // Convert the coordinates to indeces in the tile matrix.
        int[] index = convertToIndex(x, y);
        return tileMatrix[index[0]][index[1]];
    }

    /**
     * Convert the given coordinates to indeces for the tile matrix.
     * (-width/2, -height/2) is the top right corner of the matrix.
     * This corresponds to [0][0] in the two-dimensional-array-
     * @param x
     * @param y
     * @return
     */
    private int[] convertToIndex(int x, int y){
        int[] indices = new int[2];
        int newX = x + width/2;
        int newY = y + height/2;
        indices[0] = newX;
        indices[1] = newY;
        return indices;
    }

    /**
     * Return whether the given coordinates correspond to a valid tile in the matrix.
     * @param x
     * @param y
     * @return
     */
    private boolean isValidTile(int x, int y) {
        int[] indices = convertToIndex(x, y);

        // If either index is invalid, the coordinates must be invalid.
        if(!isValidXIndex(indices[0]) || !isValidYIndex(indices[1])) {
            return false;
        }

        return true;
    }

    /**
     * Return whether the given index exists in the matrix.
     * @param xIndex
     * @return
     */
    private boolean isValidXIndex(int xIndex) {
        if(xIndex >= tileMatrix[0].length || xIndex < 0) {
            return false;
        }

        return true;
    }

    /**
     * Return whether the given index exists in the matrix.
     * @param yIndex
     * @return
     */
    private boolean isValidYIndex(int yIndex) {
        if(yIndex >= tileMatrix.length || yIndex < 0) {
            return false;
        }

        return true;
    }

	public String toString()
	{
		final int STARTX = -(width/2);
		final int STARTY = -(height/2);

		StringBuilder sb = new StringBuilder();
		for (int currentYOffset = 0
			 ; currentYOffset < height; currentYOffset++)
		{
			for (int currentXOffset = 0; currentXOffset < width;
				 currentXOffset++)
			{
				Tile currentTile = getTile(STARTX + currentXOffset,
						STARTY + currentYOffset);

				sb.append(currentTile.toString());
			}
			sb.append("\n");
		}
		int lastIndex = sb.length() - 1;

		//Removes final \n
		//o 180503
		sb.deleteCharAt(lastIndex);

		return sb.toString();
	}

	/**
	 * Adds a horizontal wall to the map.
	 * @param from the x-index the wall starts at
	 * @param to the x-index the wall ends at
	 * @param row what row the wall goes to
	 */
	public void addHorizontalWall(int from, int to, int row)
	throws IndexOutOfBoundsException, IllegalArgumentException
	{
		if (to < from)
		{
			throw new IllegalArgumentException();
		}
		Tile throwAway;
		try
		{
			throwAway = getTile(from, row);
			throwAway = getTile(to, row);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException();
		}
		int tilesToChange = to - from + 1;
		for (int currentXOffset = 0; currentXOffset < tilesToChange;
			 currentXOffset++)
		{
			getTile(from + currentXOffset, row).setType(true);
		}
	}

	/**
	 * Adds a vertical wall to the map.
	 * @param from the x-index the wall starts at
	 * @param to the x-index the wall ends at
	 * @param column what column the wall is in.
	 */
	public void addVerticalWall(int from, int to, int column)
	{
		if (to < from)
		{
			throw new IllegalArgumentException();
		}
		Tile throwAway;
		try
		{
			throwAway = getTile(from, column);
			throwAway = getTile(to, column);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException();
		}
		int tilesToChange = to - from + 1;
		for (int currentYOffset = 0; currentYOffset < tilesToChange;
			 currentYOffset++)
		{
			getTile(column, from + currentYOffset).setType(true);
		}
	}

	/**
	 * Partitions the map as described in the document. This fills the
	 * room matrix with room numbers, corresponding to the different rooms.
	 *
	 * This method can, and will be modified in the future to add the walls to
	 * the map.
	 * @throws IllegalStateException if the map is 5x5 or smaller, where there
	 * is only one possible partition.
	 */
	public void partition() throws IllegalStateException
	{
		if (width < 6 || height < 6)
		{
			throw new IllegalStateException();
		}

		int roomNumber = 1;
		final int STARTX = -(width/2);
		final int STARTY = -(height/2);
		int yOffset = 0;
		int xOffset = 1;
		int xRoomEnd;
		int yRoomEnd;
		while (yOffset < height -1)
		{


			while (xOffset < width)
			{
				if (getRoom(STARTX + xOffset, STARTY + yOffset) == 0)
				{
					int xRoomStart = STARTX + xOffset;
					int yRoomStart = STARTY + yOffset;
					yRoomEnd = roomEnd(yRoomStart, height + STARTY - 2);
					xRoomEnd = roomEnd(xRoomStart, width + STARTX - 1);
					rectangleRoom(xRoomStart, xRoomEnd,
							yRoomStart, yRoomEnd, roomNumber);
					roomNumber++;
					//Old pre-mature optimization. (Does not work)
					//-O 0506
					//int gainedTiles = xRoomEnd - STARTX + 1;
					//xOffset += gainedTiles;

					//For debugging
					//-O 0506
					//System.out.println("Made room from: " + (STARTX + xOffset)
					//		+ " to: " + xRoomEnd);
					xOffset++;

				}
				else
				{
					xOffset++;
				}
			}
			xOffset = 1;
			yOffset++;
			//System.out.println("Next row: " + yOffset);
		}

		//Add the walls in the top and left part of the screen.
		addHorizontalWall(STARTX, STARTX + width - 1, STARTY + height - 1);
		addVerticalWall(STARTY, STARTY + height - 1, STARTX);

	}


	private int roomEnd(int start, int max)
	{
		int workspace = max - start;
		if (workspace < 6)
		{
			return max ;
		}
		int randomNumber = random.nextInt(workspace - 5);
		if(randomNumber == workspace - 6)
		{
			return max ;
		}
		else
		{
			return start + 2 + randomNumber;
		}
	}

	//This function creates rooms. It also adds walls in their bottom-right bit.
	// Functions relying on this: partition
	// -O 0506
	private void rectangleRoom(int leftX, int rightX, int bottomY, int topY,
							   int roomNumber)
	{
		int roomWidth = rightX - leftX + 1;
		int roomHeight = topY - bottomY + 1;

		for (int yOffset = 0; yOffset < roomHeight; yOffset++)
		{
			for (int xOffset = 0; xOffset < roomWidth; xOffset++)
			{
				int[] indeces = convertToIndex(leftX + xOffset,
						bottomY + yOffset);
				roomMatrix[indeces[0]] [indeces[1]] = roomNumber;
			}
		}
		addVerticalWall(bottomY, topY, rightX);
		addHorizontalWall(leftX, rightX - 1, bottomY);
	}

	public int getRoom(int x, int y) throws IndexOutOfBoundsException
	{
		if(!isValidTile(x,y)) {
			throw new IndexOutOfBoundsException("Tile is out of bounds.");
		}
		// Convert the coordinates to indeces in the tile matrix.
		int[] index = convertToIndex(x, y);

		return roomMatrix[index[0]][index[1]];
	}


    public void draw(Graphics g, int width, int height) {
	    int tileWidth = width / this.width;
	    int tileHeight = height / this.height;

	    for(int x = 0; x < tileMatrix.length; x++) {
	        for(int y = 0; y < tileMatrix[x].length; y++) {
	            Tile tile = tileMatrix[x][y];

	            if(tile.isWall()) {
	                g.setColor(Color.BLACK);
                }
                else {
	                g.setColor(Color.WHITE);
                }

                g.fillRect(x*tileWidth, y*tileHeight, tileWidth, tileHeight);
            }
        }
    }
}
