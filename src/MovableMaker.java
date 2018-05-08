public class MovableMaker
{
	public static Movable create(MovableType type)
	{
		switch (type)
		{
			case ROACH:
				return new Roach();
			case PLAYER:
				return new Player();
			case SWORD:
				return new Sword();
			default:
				throw new IllegalArgumentException("Non-implemented movable.");
		}
	}
}
