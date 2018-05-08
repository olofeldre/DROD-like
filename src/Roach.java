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

    @Override
	public void act(int playerX, int playerY)
	{
		Direction d;
		if (x> playerX&& y > playerY)
		{
			d = Direction.DOWNLEFT;
		}

		else if (x> playerX&& y < playerY)
		{
			d = Direction.UPLEFT;
		}

		else if (x < playerX&& y < playerY)
		{
			d = Direction.UPRIGHT;
		}
		else if (x < playerX&& y > playerY)
		{
			d = Direction.DOWNRIGHT;
		}
		else if (x == playerX && y < playerY )
		{
			d = Direction.RIGHT;
		}

		else if (x == playerX && y < playerY )
		{
			d = Direction.LEFT;
		}

		else if ( x < playerX && y == playerY)
		{
			d = Direction.UP;
		}
		else if ( x > playerX && y == playerY)
		{
			d = Direction.DOWN;
		}
		switch (d)
		{
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT:
			case UPLEFT:
			case UPRIGHT:
			case DOWNRIGHT:
			case DOWNLEFT:
		}

	}
	private void tryMove(Direction d)
	{
		switch (d)
		{
			case UP:
			case DOWN:
			case LEFT:
			case RIGHT:
			case UPLEFT:
			case UPRIGHT:
			case DOWNRIGHT:
			case DOWNLEFT:
		}
	}
}
