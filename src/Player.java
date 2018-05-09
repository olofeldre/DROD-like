import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * The game's player. It should be able to draw itself and move to different
 * tiles on the map.
 */
public class Player extends Movable {

    public Player(int x, int y)
	{
		super(x, y);
		this.type = MovableType.PLAYER;
		this.direction = Direction.UP;
	}
	

	@Override
	public void display(Graphics graphics, int x, int y) {
    	BufferedImage sprite = Resource.getImage("player");
    	double drawAngle = -Math.toRadians(Angle.getAngle(direction));
    	int centerX = x;
    	int centerY = y;

    	RenderRotate.renderSprite(graphics, centerX, centerY, drawAngle, sprite);
	}

	public void rotate(Direction right, Map map) {
    	//TODO
	}
}
