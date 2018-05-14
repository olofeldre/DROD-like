public class MapGenerator {
	private int[] playerPos;

    public static Map testMap() {
        Map map = new Map(10, 10);
        map.addHorizontalWall(-5, 4, 4);
        map.addHorizontalWall(-5, 4, -5);
        map.addVerticalWall(-5 ,4, -5);
        map.addVerticalWall(-5 ,4, 4);

        map.addVerticalWall(2, 3, -2);
        map.addVerticalWall(-4, -2, -2);
        map.addHorizontalWall(0, 1, 0);
        map.addVerticalWall(-4, -2, 1);

        map.createMovable(2, -3, MovableType.PLAYER);
        map.createMovable(2,2, MovableType.ROACH);
        map.createMovable(-4,-3, MovableType.ROACH);
        map.createMovable(-3,-4, MovableType.ROACH);

        return map;
    }

    public MapGenerator()
	{}

    public Map randomMap()
	{
		Map map = new Map(30, 30);
		map.partition();
		playerPos = map.addMovables();

		return map;
	}

	public int[] getPlayerPos()
	{
		return playerPos;
	}
}
