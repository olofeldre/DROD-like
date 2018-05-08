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

}
