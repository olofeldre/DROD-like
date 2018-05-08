import java.awt.*;

public class Roach extends Enemy {
    public Roach(int x, int y) {
        super(x,y);
        this.type = MovableType.ROACH;
    }

    @Override
    public void display(Graphics graphics, int x, int y) {
        graphics.setColor(Color.RED);
        graphics.fillOval(x - 5, y - 5, 10, 10);
    }

	public void act(int playerX, int playerY, Map map)
	{
		int xMove = (int)Math.signum((float)playerX - x);
		int yMove = (int)Math.signum((float)playerY - y);

		System.out.println(xMove);
		System.out.println(yMove);

		if(!tryMoveTo(x + xMove, y + yMove, map)) {
			if(!tryMoveTo(x + xMove, y, map)) {
				tryMoveTo(x, y + yMove, map);
			}
		}

	}
}
