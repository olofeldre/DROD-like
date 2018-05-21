public class MapGenerator {
	private int[] playerPos;

    public static Map testMap() {
        Map map = new Map(10, 10);
        Cartographer c = new Cartographer(map, 10, 10);
        c.addHorizontalWall(-5, 4, 4);
        c.addHorizontalWall(-5, 4, -5);
        c.addVerticalWall(-5 ,4, -5);
        c.addVerticalWall(-5 ,4, 4);

        c.addVerticalWall(2, 3, -2);
        c.addVerticalWall(-4, -2, -2);
        c.addHorizontalWall(0, 1, 0);
        c.addVerticalWall(-4, -2, 1);

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
		Cartographer maker = new Cartographer(map, 30, 30);

		maker.partition();
		playerPos = maker.addMovables();

		return maker.getMap();
	}
    
	public int[] getPlayerPos()
	{
		return playerPos;
	}
}
