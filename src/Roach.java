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
	public void move(Direction d, Map map)
	{
		map.getTile(x, y).removeMoveable();
		switch (d)
		{
			case UP:
				y = y +1;

			case DOWN:
				y = y -1);

			case LEFT:
				x = x - 1

			case RIGHT:
				x = x + 1

			case UPLEFT:
				x -= 1;
				y +=1;

			case UPRIGHT:
				x += 1;
				y +=1;

			case DOWNRIGHT:
				 x += 1;
				 y -= 1;

			case DOWNLEFT:
				x -= 1;
				y -= 1;
		}
		map.getTile(x, y).setMovable(this);
	}

    @Override
	public void act(int playerX, int playerY, Map map)
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
		boolean b = false;
		switch (d)
		{
			case UP;
				b = tryMove(Direction.UP, map);
				if (b == false)
				{
					
				}
				if(b == false)
				{

				}
			case DOWN:
			case LEFT:
			case RIGHT:
			case UPLEFT:
				tryMove(Direction.UP, map);

			case UPRIGHT:
				tryMove(Direction.UP, map);

			case DOWNRIGHT:
			case DOWNLEFT:
		}

	}
	private boolean tryMove(Direction d, Map map)
	{
		boolean ok = false
		switch (d)
		{
			case UP:
				ok = freeTile(map, x, y +1);
			case DOWN:
				ok= freeTile(map, x, y -1);

			case LEFT:
				ok=  freeTile(map, x -1, y);

			case RIGHT:
				ok= freeTile(map, x + 1, y);

			case UPLEFT:
				ok= freeTile(map, x - 1, y +1);

			case UPRIGHT:
				ok= freeTile(map, x + 1, y +1);

			case DOWNRIGHT:
				ok= freeTile(map, x +1, y -1);

			case DOWNLEFT:
				ok= freeTile(map, x - 1, y - 1);
		}
		if ok
		{
			move(d, map);
		}
		return ok;
	}

	private boolean freeTile(Map map, int x, int y)
	{
		if (map.getTile(x, y).isWall() == true)
		{
			return false;
		}
		if (map.getTile(x, y).getMovable() != null)
		{
			return false;
		}
		return true;
	}
}
