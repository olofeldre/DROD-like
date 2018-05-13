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
    	BufferedImage sprite = Resource.getImage("player");
		RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(angle), sprite);
	}


	public boolean move(Direction direction, Map map) {

		Point newPos = getNewPosition(direction);
		map.removeMovable(x + relativeSwordPos.x, y + relativeSwordPos.y);

		if(tileFree(newPos.x, newPos.y, map)) {
			Movable movable = map.getMovable(newPos.x + relativeSwordPos.x, newPos.y + relativeSwordPos.y);
			if(movable != null && movable.type == MovableType.ROACH) {
				Enemy enemy = (Enemy) movable;
				enemy.kill();
				map.removeEnemy(enemy);
			}

			super.move(direction, map);
			map.setMovable(x + relativeSwordPos.x, y + relativeSwordPos.y, sword);
			sword.x = x + relativeSwordPos.x;
			sword.y = y + relativeSwordPos.y;


			return true;
		}

		map.setMovable(x + relativeSwordPos.x, y + relativeSwordPos.y, sword);
		return false;
	}

	public void rotate(Direction dir, Map map) {
		if(dir == Direction.LEFT) {
			angle += 45;
		}
		else if(dir == Direction.RIGHT) {
			angle -= 45;
		}

		angle = Math.floorMod(angle, 360);

		map.removeMovable(x + relativeSwordPos.x, y + relativeSwordPos.y);
		sword.setAngle(angle);

		Point newPos = getNewPosition(Angle.getDirection(angle));
		relativeSwordPos.x = newPos.x - x;
		relativeSwordPos.y = newPos.y - y;

		Movable previous = map.getMovable(newPos.x, newPos.y);

		if(previous != null && previous.type == MovableType.ROACH) {
			Enemy enemy = (Enemy)previous;
			enemy.kill();
			map.removeEnemy(enemy);
		}

		sword.setPosition(newPos.x, newPos.y, map);
    }

}
