import java.awt.*;

/**
 * The game's player. It should be able to draw itself and move to different
 * tiles on the map.
 */
public class Player extends Movable {
	public Direction facing;

    public Player(int x, int y)
	{
		super(x, y);
		this.type = MovableType.PLAYER;
		facing = Direction.UP;
	}
	

	@Override
	public void display(Graphics graphics, int x, int y) {
		graphics.fillOval(x - 5, y - 5, 10, 10);
	}

	public void rotate(Direction right, Map map) {
    	//TODO
	}
}
