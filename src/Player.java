import java.awt.*;

/**
 * The game's player. It should be able to draw itself and move to different tiles on the map.
 */
public class Player extends Movable {
    public Player() {
        this.type = MovableType.PLAYER;
    }

	@Override
	public void display(Graphics graphics)
	{
		graphics.setColor(Color.getColor("Yellow"));
		//graphics.fillRect();
	}
}
