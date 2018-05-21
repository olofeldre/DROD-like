import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Cartographer
{
	private ArrayList<Wall> walls;
	private Map map;
	private int width;
	private int height;
	private int [][] roomMatrix;

	public Cartographer(Map map, int width, int height)
	{
		this.map = map;
		this.height = height;
		this.width = width;
		walls = new ArrayList<>();
		roomMatrix = new int[width][height];
	}


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
			throwAway = map.getTile(from, row);
			throwAway = map.getTile(to, row);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException();
		}
		int tilesToChange = to - from + 1;
		for (
				int currentXOffset = 0; currentXOffset < tilesToChange;
				currentXOffset++)
		{
			map.getTile(from + currentXOffset, row).setType(true);
		}

		if (row == -width / 2)
		{
			return;
		}
		walls.add(new Wall(from, to, row, false));
	}

	public void addVerticalWall(int from, int to, int column)
	{
		if (to < from)
		{
			throw new IllegalArgumentException();
		}
		Tile throwAway;
		try
		{
			throwAway = map.getTile(from, column);
			throwAway = map.getTile(to, column);
		}
		catch (IndexOutOfBoundsException e)
		{
			throw new IndexOutOfBoundsException();
		}
		int tilesToChange = to - from + 1;
		for (
				int currentYOffset = 0; currentYOffset < tilesToChange;
				currentYOffset++)
		{
			map.getTile(column, from + currentYOffset).setType(true);
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
		if (from == -height / 2)
		{
			walls.add(new Wall(from + 1, to, column, true));
			return;
		}
		walls.add(new Wall(from, to, column, true));

	}

	/**
	 * Partitions the map as described in the document. This fills the
	 * room matrix with room numbers, corresponding to the different rooms.
	 * <p>
	 * This method can, and will be modified in the future to add the walls to
	 * the map.
	 *
	 * @throws IllegalStateException if the map is 5x5 or smaller, where there
	 *                               is only one possible partition.
	 */
	public void partition() throws IllegalStateException
	{
		if (width < 6 || height < 6)
		{
			throw new IllegalStateException();
		}

		int roomNumber = 1;
		final int STARTX = -(width / 2);
		final int STARTY = -(height / 2);
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

				} else
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
		walls.remove(walls.size() - 1);
		walls.remove(walls.size() - 1);

		doors(roomNumber - 1);

	}

	private int probe(int fromX, int rowY)
	{
		int maxX;
		if (width % 2 == 0)
		{
			maxX = width / 2 - 1;
		} else
		{
			maxX = width / 2;
		}
		int currentX = fromX;
		while (currentX + 1 <= maxX)
		{
			if (getRoom(currentX + 1, rowY) != 0)
			{
				return currentX;
			}
			currentX++;
		}
		return maxX;
	}

	private int roomEnd(int start, int max)
	{
		Random random = new Random();
		int workspace = max - start;
		if (workspace < 6)
		{
			return max;
		}
		int randomNumber = random.nextInt(workspace - 5);
		if (randomNumber == workspace - 6)
		{
			return max;
		} else
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
			map.getTile(coordinates[0], coordinates[1]).setType(false);
			Integer[] neighbours = getNeighborRooms(coordinates[0], coordinates[1]);
			for (int room : neighbours)
			{
				for (int anotherRoom : neighbours)
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
				int[] indeces = map.convertToIndex(leftX + xOffset,
						bottomY + yOffset);
				roomMatrix[indeces[0]][indeces[1]] = roomNumber;
			}
		}
		addVerticalWall(bottomY, topY, rightX);
		addHorizontalWall(leftX, rightX - 1, bottomY);
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
		if(map.isWalkable(x, y))
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


	public int[] addMovables()
	{
		Random random = new Random();
		//Adjust freely.
		int roachNo = ((width * height) - 64) / 40;
		int roachesPlaced = 0;
		int atY = 0;
		int atX = 0;
		while(roachesPlaced < roachNo)
		{
			atX = -(width / 2) + random.nextInt(width);
			atY = -(height / 2) + random.nextInt(height);
			Tile currentTile = map.getTile(atX, atY);
			// To close to player!
			if (atX > -(width / 2) + width - 8 &&
					atY < -(width / 2) + 8)
			{
				continue;
			}
			if (currentTile.isWall() == false &&
					currentTile.getMovable() == null)
			{
				map.createMovable(atX, atY, MovableType.ROACH);
				roachesPlaced ++;
			}

		}

		boolean playerPlaced = false;
		while(playerPlaced == false)
		{
			//place player in lower right corner.
			atX = (width / 4) + random.nextInt(width / 4);
			atY = -(height / 2) + random.nextInt(height / 4);
			Tile currentTile = map.getTile(atX, atY);
			if (currentTile.isWall() == false &&
					currentTile.getMovable() == null)
			{
				map.createMovable(atX, atY, MovableType.PLAYER);
				playerPlaced = true;
			}

		}
		int[] returnArray = new int[2];
		returnArray[0] = atX;
		returnArray[1] = atY;
		return returnArray;

	}

	public int getRoom(int x, int y) throws IndexOutOfBoundsException
	{
		try
		{
			map.getTile(x, y);
		}
		catch(IndexOutOfBoundsException i)
		{
			throw new IndexOutOfBoundsException("Tile is out of bounds.");
		}

		// Convert the coordinates to indeces in the tile matrix.
		int[] index = map.convertToIndex(x, y);

		return roomMatrix[index[0]][index[1]];
	}

	public Map getMap()
	{
		return map;
	}
}

