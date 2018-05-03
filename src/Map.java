<<<<<<< HEAD


=======
>>>>>>> 86a304f09fe0ef61c0710fb33e7fe464b827d175
/**
 * Map: This class should store information about the map and
 * get information stored in specific tiles.
 */

public class Map {
    private int width;
    private int height;
    private Tile [][] tileMatrix;

    /**
     * Construct the map by initializing the tile matrix of size width * height
     * that stores all information on the map.
<<<<<<< HEAD
     * @param width width of map to be constructed
     * @param height ditto, but for height
=======
     * @param width
     * @param height
>>>>>>> 86a304f09fe0ef61c0710fb33e7fe464b827d175
     */
    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tileMatrix = new Tile[width][height];

        // Add empty tile elements to the entire tile matrix.
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j ++){
                tileMatrix[i][j] = new Tile(false);
            }
        }
    }

    /**
     * Return the the tile object located at (x, y) in the tile matrix.
<<<<<<< HEAD
     * @param x x-position of tile in matrix
	 *          origin of matrix is in the centre of the map.
     * @param y y-position of tile in the matrix.
     * @return Tile at coordinates
     * @throws IndexOutOfBoundsException if index does lies outside the map.
=======
     * @param x
     * @param y
     * @return Tile at coordinates
     * @throws IndexOutOfBoundsException
>>>>>>> 86a304f09fe0ef61c0710fb33e7fe464b827d175
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 86a304f09fe0ef61c0710fb33e7fe464b827d175
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
}
