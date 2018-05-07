public class Game {
    public static void main(String[] args) {
        //System.out.println("Hello, World!");
		Map testMap = new Map(20, 20);
		testMap.partition();
		System.out.println(testMap.roomString());
		System.out.println(testMap);

		//testMap.addHorizontalWall(-10, 9, 9);
		//System.out.println(testMap);

	}
}
