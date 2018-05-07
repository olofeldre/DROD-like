import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

public class MapTest {
    private Map map;
    private Map oddMap;
    @Before
    public void setup() {
        map = new Map(50, 50);
        oddMap = new Map(51, 51);
    }

    @Test
    public void getTileShouldReturnValidTile() {
        Tile tile = map.getTile(2, 5);
        Tile negativeTile = map.getTile(-1, -1);

        assertThat(tile, notNullValue());
        assertThat(negativeTile, notNullValue());
    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void getTileThrowIfBadX(){
        Tile badXTile = map.getTile(-30, 5 );
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void getTileThrowIfBadY(){
        Tile badXTile = map.getTile(-3, 40 );

    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void getTileThrowIfBadXandBadY(){
        Tile badXTile = map.getTile(-30, 28 );
    }

    @Test
    public void getTileOnEdgeShouldNotThrowException() {
        Map map = new Map(4, 4);
        map.getTile(-2,1);
    }

    @Test
    public void getTileOnEdgeOnOddSizeMapShouldNotThrowException() {
        Map map = new Map(5, 5);
        map.getTile(2,2);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void getTileOutsideMapShouldThrowException() {
        Map map = new Map(4, 4);
        map.getTile(2, 0);
    }

    @Test(expected=IndexOutOfBoundsException.class)
    public void getTileOutsideOddMapShouldThrowException()
	{
        Map map = new Map(5, 5);
        map.getTile(3, 3);
    }

    @Test
	public void getTileInCornerOfEvenMapShouldNotThrowException()
	{
		map.getTile(24, 24);
	}

	@Test
    public void toStringWorksOnEmptyMaps()
	{
		//Arrange
		String targetString;
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 4; i ++)
		{
			stringBuilder.append("-----\n");
		}
		stringBuilder.append("-----");
		targetString = stringBuilder.toString();

		Map emptyMap = new Map(5, 5);

		//Act
		String compareString = emptyMap.toString();

		//Assert
		assertEquals(targetString, compareString);
	}

	@Test
	public void addHorizontalWallAddsWalls()
	{
		//Arrange
		Map map = new Map(5, 5);

								// from, to, row (both including)
		//Act
		map.addHorizontalWall(-2, 0, 0);

		//Assert
		assertTrue(map.getTile(-2, 0).isWall());
	}

	@Test (expected =  IllegalArgumentException.class)
	public void addHorizontalWallThrowsIfFromLargerThanTo()
	{
		map.addHorizontalWall(5, -2, 3);
	}

	@Test (expected =  IndexOutOfBoundsException.class)
	public void addHorizontalWalThrowsIfBadArgsAreGiven()
	{
		map.addHorizontalWall(14, 100, 2);
	}

	public void addVerticalWallAddsWalls()
	{
		//Arrange
		Map map = new Map(5, 5);

		// from, to, column (both including)
		//Act
		map.addVerticalWall(-2, 0, 0);

		//Assert
		assertTrue(map.getTile(0, 0).isWall());
	}

	@Test
	public void addHorizontalWallWorksInEdges()
	{
		//Arrange
		Map map = new Map(5, 5);

		//Act
		map.addHorizontalWall(-2, 2, -2);

		//Assert
		assertTrue(map.getTile(2, -2).isWall());
	}

	@Test
	public void addVerticalWallWorksInEdges()
	{
		//Arrange
		Map map = new Map(5, 5);

		//Act
		map.addVerticalWall(-2, 2, -2);

		//Assert
		assertTrue(map.getTile(-2, 0).isWall());
	}

	@Test (expected =  IllegalArgumentException.class)
	public void addVerticalWallThrowsIfFromLargerThanTo()
	{
		map.addVerticalWall(5, -2, 3);
	}

	@Test (expected =  IndexOutOfBoundsException.class)
	public void addVerticalWallThrowsIfBadArgsAreGiven()
	{
		map.addVerticalWall(-75, -4, -10);
	}

	@Test
	public void partitionGivesTilesRooms()
	{
		// Act
		map.partition();
		oddMap.partition();

		assertNotEquals(map.getRoom(18, 5), 0);
		assertNotEquals(oddMap.getRoom(18, 5), 0);

	}

	@Test
	public void unpartitionedMapsHaveNoRooms()
	{
		assertEquals(map.getRoom(4,3), 0);
		assertEquals(oddMap.getRoom(4, 3), 0);
	}

	@Test
	public void partitionGivesAlmostAllRoomNumbers()
	{
		//Act
		map.partition();

		//Assert
		for(int column = -24; column < 25; column++)
		{
			for (int row = -25; row < 24; row++)
			{
				assertNotEquals(map.getRoom(column, row), 0);
			}
		}
	}

	@Test
	public void partitionGivesAlmostAllRoomNumbersInOddMaps()
	{
		//Act
		oddMap.partition();

		//Assert
		for(int column = -24; column < 26; column++)
		{
			for (int row = -25; row < 25; row++)
			{
				assertNotEquals(oddMap.getRoom(column, row), 0);
			}
		}
	}


	@Test
	public void partitionLeavesTheFirstColumnAndRowBlank()
	{
		//Act
		map.partition();

		//Assert

		for(int column = -25; column < 25; column++)
		{
			assertEquals(map.getRoom(column, 24), 0);
		}
		System.out.println("Top row ok!");

		for(int row = -25; row < 25; row++)
		{
			assertEquals(map.getRoom(-25, row), 0);
		}
		System.out.println("Left col ok!");

	}

	@Test
	public void partitionAddsWallInTopLeftCorner()
	{
		//Act
		map.partition();
		oddMap.partition();

		//Assert
		assertEquals(true, map.getTile(-25, 24).isWall() );
		assertEquals(true, oddMap.getTile(-25, 25).isWall() );
	}

	@Test
	public void partitionAddsWallInBottomRightCorner()
	{
		//Act
		oddMap.partition();
		map.partition();

		//Assert
		assertEquals(true, map.getTile(24, -25).isWall() );
		assertEquals(true, oddMap.getTile(25, -25).isWall() );

	}

	@Test
	public void roomStringHasZeros()
	{
		map = new Map(5, 5);
		assertTrue(map.roomString().startsWith("0") );
	}
}