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
     * @param width
     * @param height
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
     * @param x
     * @param y
     * @return Tile at coordinates
     * @throws IndexOutOfBoundsException
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


}
