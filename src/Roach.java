import java.awt.*;
import java.security.cert.X509Certificate;

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
		// positive if player is right or above roach -o 0509
		int xMove = (int) Math.signum((float) playerX - x);
		int yMove = (int) Math.signum((float) playerY - y);


		System.out.println(xMove);
		System.out.println(yMove);
		Direction d = Direction.UP;
		boolean success = false;
		int twoIfDiagonal = Math.abs(xMove) + Math.abs(yMove);
		success = tryMoveTo(x + xMove, y + yMove, map);
		if (success)
		{
			return;
		}
		switch (twoIfDiagonal)
		{
			// If moving diagonally is preferrable.
			//O 0509
			case 2:
				success = tryMoveTo(x + xMove, y, map);
				if (success)
				{
					return;
				}
				tryMoveTo(x, y + yMove, map);
				return;

			//If moving diagonally offers no advantage.
			//O 0509
			case 1:

				switch (Math.abs(xMove))
				{
					//prefers vertical.
					case 0:
						success = tryMoveTo(x + 1, y + yMove, map);
						if (success)
						{
							return;
						}
						success = tryMoveTo(x - 1, y + yMove, map);
						return;

						//prefers horizontal.
					case 1:
						success = tryMoveTo(x + xMove, y + 1, map);
						if(success)
						{
							return;
						}
						tryMoveTo(x + xMove, y - 1, map);
						return;
					default:
						throw new IllegalStateException("Error on move.");
				}
			default:
				return;
		}
	}


	private boolean tryMove(Direction d, Map map)
	{
		boolean ok = false;
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
		if (ok)
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
