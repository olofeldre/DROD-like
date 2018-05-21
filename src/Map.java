import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashSet;
import java.util.Iterator;

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

	private Random random;
	private LinkedList<Enemy> enemies;


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

        // Add empty tile elements to the entire tile matrix.
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j ++){
                tileMatrix[i][j] = new Tile(false);
            }
        }

        enemies = new LinkedList<>();
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
     * (-width/2, height/2) is the top left corner of the matrix.
     * This corresponds to [0][0] in the two-dimensional-array-
     * @param x
     * @param y
     * @return
     */
    public int[] convertToIndex(int x, int y){
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
		Movable movable = MovableMaker.create(type, x, y, this);
		tile.setMovable(movable);

		if(type == MovableType.ROACH) {
			enemies.add((Enemy) movable);
		}

		return movable;
	}

	public Movable getMovable(int x, int y) {
		return getTile(x, y).getMovable();
	}

	public void removeMovable(int x, int y) {
		getTile(x, y).setMovable(null);
	}

	public void setMovable(int x, int y, Movable movable) {
		getTile(x, y).setMovable(movable);
	}

	public void removeEnemy(Enemy enemy) {
		enemies.remove(enemy);
	}

	public void updateEnemies(int playerX, int playerY)
	{
		for(Enemy enemy: enemies)
		{
			if(enemy.isAlive()) {
				enemy.act(playerX, playerY, this);
			}
		}


		Iterator<Enemy> iterator = enemies.iterator();

		while(iterator.hasNext()) {
			Enemy e = iterator.next();

			if(!e.isAlive()) {
				removeMovable(e.x, e.y);
				iterator.remove();
			}
		}
	}



	public int getNumberOfEnemies() {
		return enemies.size();
	}


}
