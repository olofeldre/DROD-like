import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * The game's player. It should be able to draw itself and move to different
 * tiles on the map.
 */
public class Player extends Movable {
	private int angle = 0;
	private Sword sword;

    public Player(int x, int y)
	{
		super(x, y);
		this.type = MovableType.PLAYER;
		this.direction = Direction.UP;
	}
	

	@Override
	public void display(Graphics graphics, int x, int y) {
    	BufferedImage sprite = Resource.getImage("player");
		RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(Angle.getAngle(direction)), sprite);
	}

	public void rotate(Direction dir, Map map) {
		//TODO
	}
}
