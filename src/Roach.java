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

<<<<<<< HEAD
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
=======
>>>>>>> f10f4845be65dfdb356e64d99108b2ddd44bbd66
	}
}
