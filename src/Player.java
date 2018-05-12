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
	private Point relativeSwordPos;

    public Player(int x, int y, Map map)
	{
		super(x, y);
		this.type = MovableType.PLAYER;
		this.direction = Direction.UP;
		sword = (Sword) map.createMovable(x + 1, y, MovableType.SWORD);
		relativeSwordPos = new Point(1, 0);
	}
	

	@Override
	public void display(Graphics graphics, int x, int y) {
    	System.out.println(sword.x + ", " + sword.y);
    	BufferedImage sprite = Resource.getImage("player");
		RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(angle), sprite);
	}

	public boolean move(Direction direction, Map map) {

		Point newPos = getNewPosition(direction);
		Tile newTile = map.getTile(newPos.x, newPos.y);
		Movable movable = map.getMovable(newPos.x, newPos.y);

		if(!newTile.isWall() && (movable == null || movable.type == MovableType.SWORD)) {
			map.removeMovable(x + relativeSwordPos.x, y + relativeSwordPos.y);
			super.move(direction, map);
			map.setMovable(x + relativeSwordPos.x, y + relativeSwordPos.y, sword);
			return true;
		}

		return false;
	}

	public void rotate(Direction dir, Map map) {
		if(dir == Direction.LEFT) {
			angle += 45;
		}
		else if(dir == Direction.RIGHT) {
			angle -= 45;
		}

		map.removeMovable(x + relativeSwordPos.x, y + relativeSwordPos.y);
		sword.setAngle(angle);
		Point newPos = getNewPosition(Angle.getDirection(angle));
		sword.setPosition(newPos.x, newPos.y, map);
    }

}
