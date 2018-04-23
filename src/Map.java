public class Map {
    private int width;
    private int height;
    private Tile [][] tileMatrix;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        tileMatrix = new Tile[width][height];
        for (int i = 0; i < width; i++){
            for(int j = 0; j < height; j ++){
                tileMatrix[i][j] = new Tile(false);
            }
        }
    }

    public Tile getTile(int x, int y) throws IndexOutOfBoundsException {
        if(!isValidTile(x,y)) {
            throw new IndexOutOfBoundsException("Tile is out of bounds.");
        }
        int[] index = convertToIndex(x, y);
        return tileMatrix[index[0]][index[1]];
    }

    private int[] convertToIndex(int x, int y){
        int[] indices = new int[2];
        int newX = x + width/2;
        int newY = y + height/2;
        indices[0] = newX;
        indices[1] = newY;
        return indices;
    }

    private boolean isValidTile(int x, int y) {
        if(x >= -width/2 && x < Math.ceil(width/2f)) {
            if(y >= -height/2 && y < Math.ceil(height/2f)) {
                return true;

            }
        }
        return  false;
    }


}
