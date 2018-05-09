import java.awt.*;
import java.awt.image.BufferedImage;

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
    	BufferedImage sprite = Resource.getImage("player");
		graphics.drawImage(sprite, x - sprite.getWidth()/2, y - sprite.getHeight()/2, null);
	}

	public void rotate(Direction right, Map map) {
    	//TODO
	}
}
