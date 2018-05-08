public class Tile {
    private boolean wall;
    private Movable movable;

    public Tile(boolean wall){
        this.wall = wall;
    }

    public void setType(boolean wall) {
        this.wall = wall;
    }

    public void setMovable(Movable movable) {
        this.movable = movable;
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

    public Movable getMovable() {
        return movable;
    }

    public void removeMoveable()
	{
		movable	= null;
	}
}
