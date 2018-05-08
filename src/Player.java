import java.awt.*;

/**
 * The game's player. It should be able to draw itself and move to different tiles on the map.
 */
public class Player extends Movable {
    public Player(int x, int y) {
		super(x,y);
		this.type = MovableType.PLAYER;
    }

	@Override
	public void display(Graphics graphics, int x, int y) {
		graphics.setColor(Color.YELLOW);
		graphics.drawOval(x - 5, y - 5, 10, 10);
	}

	public void move(Direction direction, Map map) {
    	int newX = x;
    	int newY = y;

    	switch(direction) {
			case LEFT: newX -= 1; break;
			case RIGHT: newX += 1; break;
			case UP: newY += 1; break;
			case DOWN: newY -= 1; break;
			case UPLEFT: newX -= 1; newY += 1; break;
			case UPRIGHT: newX += 1; newY += 1; break;
			case DOWNLEFT: newX -= 1; newY -= 1; break;
			case DOWNRIGHT: newX += 1; newY -= 1; break;
		}

		if(!map.getTile(newX, newY).isWall()) {
			map.getTile(x, y).removeMovable();
    		x = newX;
    		y = newY;
    		map.getTile(x, y).setMovable(this);
		}
	}
}
