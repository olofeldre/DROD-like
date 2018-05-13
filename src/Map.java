import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Random;
import java.util.LinkedList;
import java.util.ArrayList;

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
	private LinkedList<Enemy> enemies;
	private ArrayList<Wall> walls;
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

        enemies = new LinkedList<>();
        random = new Random();
        walls = new ArrayList<>();
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
     * (-width/2, height/2) is the top left corner of the matrix.
     * This corresponds to [0][0] in the two-dimensional-array-
     * @param x
     * @param y
     * @return
     */
    private int[] convertToIndex(int x, int y){
        int[] indices = new int[2];
        int newX = x + width/2;
		int newY;
		if (height % 2 == 0)
		{
			newY = height / 2 - y - 1;
		}
		else
		{
			newY = height / 2 - y;
		}
		//newY = height / 2 + y;
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

		StringBuilder sb = new StringBuilder();
		for (int currentYOffset = 0
			 ; currentYOffset < height; currentYOffset++)
		{
			for (int currentXOffset = 0; currentXOffset < width;
				 currentXOffset++)
			{
				Tile currentTile = tileMatrix[currentXOffset] [currentYOffset];

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

		if (row == - width/2)
		{
			return;
		}
		walls.add(new Wall(from,  to, row, false));
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
		int maxCol = height / 2;
		if (height % 2 == 0)
		{
			maxCol -= 1;
		}
		if (column == maxCol)
		{
			return;
		}

		//Make sure the wall doesn't leak into the bottom of the map.
		//That can cause doors to create a hole there.
		//TODO:
		//Make a unit test for this case.
		// -O 0513
		if (from == - height / 2)
		{
			walls.add(new Wall(from + 1, to, column, true));
			return;
		}
		walls.add(new Wall(from,  to, column, true));

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
		while (yOffset < height - 1)
		{


			while (xOffset < width)
			{
				if (getRoom(STARTX + xOffset, STARTY + yOffset) == 0)
				{
					int xRoomStart = STARTX + xOffset;
					int yRoomStart = STARTY + yOffset;
					int xRoomEndScope = probe(xRoomStart, yRoomStart);

					yRoomEnd = roomEnd(yRoomStart, height + STARTY - 2);
					xRoomEnd = roomEnd(xRoomStart, xRoomEndScope);
					rectangleRoom(xRoomStart, xRoomEnd,
							yRoomStart, yRoomEnd, roomNumber);
					roomNumber++;
					//Old pre-mature optimization. (Does not work)
					//-O 0506
					//int gainedTiles = xRoomEnd - STARTX + 1;
					//xOffset += gainedTiles;

					//For debugging
					//-O 0506
		//			System.out.println("Made room from: " + (STARTX + xOffset)
		//					+ " to: " + xRoomEnd);
					xOffset++;

				}
				else
				{
					xOffset++;
				}
			}
			xOffset = 1;
			yOffset++;
		//	System.out.println("Next row: " + yOffset);
		}

		//Add the walls in the top and left part of the screen.
		addHorizontalWall(STARTX + 1, STARTX + width - 1,
				STARTY + height - 1);
		addVerticalWall(STARTY, STARTY + height - 1, STARTX);
		walls.remove(walls.size() -1);
		walls.remove(walls.size() -1);

		doors(roomNumber - 1);

	}

	private int probe(int fromX, int rowY)
	{
		int maxX;
		if (width % 2 == 0)
		{
			maxX =  width / 2  - 1;
		}
		else
		{
			maxX = width / 2;
		}
		int currentX = fromX;
		while (currentX + 1 <= maxX)
		{
			if (getRoom(currentX + 1, rowY) != 0 )
			{
				return currentX;
			}
			currentX ++;
		}
		return maxX;
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

	private void doors(int maxNo)
	{
		Random random = new Random();
		HashGraph connections = new HashGraph(maxNo);
		int topWall = walls.size() - 1;
		GraphCalculations gc = new GraphCalculations();
		Wall currentWall;
		int iterations = 0;
		while (gc.components(connections) > 1)
		{
			int randomWallIndex = random.nextInt(topWall);
			currentWall = walls.get(randomWallIndex);
			int[] coordinates = currentWall.getRandomWallCoordinate();
			getTile(coordinates[0], coordinates[1]).setType(false);
			Integer [] neighbours = getNeighborRooms(coordinates[0], coordinates[1]);
			for (int room : neighbours)
			{
				for(int anotherRoom : neighbours)
				{
					connections.addBi(room - 1, anotherRoom - 1);
				}
			}
			if (iterations > 100)
			{
				throw new IllegalStateException("To many iterations long!");
			}
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

	public Integer[] getNeighborRooms(int x, int y)
	{
		HashSet<Integer> neighbors = new HashSet<Integer>();

		int currentRoom = getRoom(x, y);
		neighbors.add(currentRoom);

		//up
		neighbors.add ( getWalkyRoom(x, y + 1));
		//upleft
		neighbors.add ( getWalkyRoom(x - 1, y + 1));
		//upright
		neighbors.add ( getWalkyRoom(x + 1, y + 1));
		//right
		neighbors.add ( getWalkyRoom(x +1, y ));
		//left
		neighbors.add (  getWalkyRoom(x -1, y));
		//downleft
		neighbors.add ( getWalkyRoom(x - 1, y - 1));
		//down
		neighbors.add (  getWalkyRoom(x, y - 1));
		//downright
		neighbors.add (  getWalkyRoom(x + 1, y - 1));

		neighbors.remove(-1);
		Integer[] returnArray = new Integer[neighbors.size()];
		return neighbors.toArray(returnArray);
	}

	private int getWalkyRoom(int x, int y)
	{
		int roomNo;
		try
		{
			 roomNo = getRoom(x, y);
		}
		catch(IndexOutOfBoundsException o)
		{
			return -1;
		}
		if(isWalkable(x, y))
		{
			return roomNo;
		}
		else
		{
			return -1;
		}
	}

	public String roomString()
	{
		StringBuilder sb = new StringBuilder();
		for (int currentYOffset = 0
			 ; currentYOffset < height; currentYOffset++)
		{
			for (int currentXOffset = 0; currentXOffset < width;
				 currentXOffset++)
			{
				int currentRoomNo = roomMatrix[currentXOffset][currentYOffset];

				sb.append(currentRoomNo);
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
	 * Draw the map.
	 * @param g
	 */
	public void draw(Graphics g, int windowWidth, int windowHeight)
	{
		int tileWidth = windowWidth / width;
		int tileHeight = windowHeight / height;

		Color wallColor = new Color(170, 85, 0);
		Color floorColor = Color.BLACK;
		Color tileColor = Color.GREEN;

		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				Tile tile = tileMatrix[x][y];
				if(tile.isWall()) {
					g.setColor(wallColor);
					g.fillRect(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
				}
				else {
					g.setColor(floorColor);
					g.fillRect(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
					g.setColor(tileColor);
					g.fillRect(x*tileWidth + tileWidth / 2, y*tileHeight + tileHeight / 2,2, 2);
				}

				if(tile.getMovable() != null) {
				    tile.getMovable().display(g, x*tileWidth + tileWidth/2, y*tileHeight + tileHeight/2);
                }
			}
		}
	}

	/**
	 * Get a tile's center position.
	 * @param x
	 * @param y
	 * @param windowWidth
	 * @param windowHeight
	 * @return the center of the tile.
	 * @throws IndexOutOfBoundsException if the given tile is outside the map.
	 */
	public Point getTileCenter(int x, int y, int windowWidth, int windowHeight) throws IndexOutOfBoundsException {
		if(!isValidTile(x, y)) {
			throw new IndexOutOfBoundsException("Tile is outside bounds");
		}

		int[] indeces = convertToIndex(x, y);
		int tileWidth = windowWidth / width;
		int tileHeight = windowHeight / height;

		int pixelX = indeces[0];
		int pixelY = indeces[1];

		return new Point(pixelX * tileWidth + tileWidth / 2,pixelY * tileHeight + tileHeight / 2);
	}

	/**
	 * Return whether or not the given tile at coordinates (x, y) can be walked on.
	 * @return
	 */
	public boolean isWalkable(int x, int y)
	{
		return !getTile(x, y).isWall();
	}

	public Movable createMovable(int x, int y, MovableType type) {
		Tile tile = getTile(x, y);
		Movable movable = MovableMaker.create(type, x, y);
		tile.setMovable(movable);

		if(type == MovableType.ROACH) {
			enemies.add((Enemy) movable);
		}

		return movable;
	}

	public void updateEnemies(int playerX, int playerY)
	{
		for(Enemy enemy: enemies)
		{
			enemy.act(playerX, playerY, this);
		}

	}



}
