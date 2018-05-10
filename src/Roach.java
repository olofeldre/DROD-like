import java.awt.*;
import java.awt.image.BufferedImage;
import java.security.cert.X509Certificate;

public class Roach extends Enemy {
    public Roach(int x, int y) {
        super(x,y);
        this.type = MovableType.ROACH;
        this.direction = Direction.DOWN;
    }

    @Override
    public void display(Graphics graphics, int x, int y) {
        BufferedImage sprite = Resource.getImage("roach");
        RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(Angle.getAngle(direction)), sprite);

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
}
