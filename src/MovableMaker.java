public class MovableMaker
{
	public static Movable create(MovableType type, int x, int y, Map map)
	{
		switch (type)
		{
			case ROACH:
				return new Roach(x, y);
			case PLAYER:
				return new Player(x, y, map);
			case SWORD:
				return new Sword(x, y);
			default:
				throw new IllegalArgumentException("Non-implemented movable.");
		}
	}
}
