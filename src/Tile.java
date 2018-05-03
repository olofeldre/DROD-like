public class Tile {
    private boolean wall;

    public Tile(boolean wall){
        this.wall = wall;
    }

    public void setType(boolean wall) {
        this.wall = wall;
    }

    public String toString()
	{
        if (wall == true)
		{
			return "@";
		}
		else
			return "-";
    }

    public boolean isWall() {
        return wall;
    }
}
