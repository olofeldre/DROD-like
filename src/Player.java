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

    public Player(int x, int y, Map map)
	{
		super(x, y);
		this.type = MovableType.PLAYER;
		this.direction = Direction.UP;
		sword = (Sword) map.createMovable(x+1, y, MovableType.SWORD);
	}
	

	@Override
	public void display(Graphics graphics, int x, int y) {
    	BufferedImage sprite = Resource.getImage("player");
		RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(angle), sprite);
	}

	public boolean move(Direction direction, Map map) {
    	int oldSwordX = sword.x;
    	int oldSwordY = sword.y;

		sword.move(direction, map);
    	if(!super.move(direction, map)) {
    		sword.tryMoveTo(oldSwordX, oldSwordY, map);
    		return false;
		}

		return true;
	}

	public void rotate(Direction dir, Map map) {

    }

    private void setSwordPosition() {

	}
}
