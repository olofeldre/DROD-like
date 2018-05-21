import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNotEquals;

public class CartographerTest
{
	private Map map;
	private Map oddMap;
	private Cartographer mapMan;
	private Cartographer oddMapMan;

	@Before
	public void setup()
	{
		map = new Map(50, 50);
		oddMap = new Map(51, 51);
		mapMan = new Cartographer(map, 50, 50);
		oddMapMan = new Cartographer(oddMap, 51, 51);
	}

	@Test
	public void addHorizontalWallAddsWalls()
	{
		//Arrange
		Map smallMap = new Map(5, 5);
		Cartographer smallMan = new Cartographer(smallMap, 5, 5);

		// from, to, row (both including)
		//Act
		smallMan.addHorizontalWall(-2, 0, 0);

		//Assert
		assertTrue(smallMan.getMap().getTile(-2, 0).isWall());
	}

	@Test (expected =  IllegalArgumentException.class)
	public void addHorizontalWallThrowsIfFromLargerThanTo()
	{
		mapMan.addHorizontalWall(5, -2, 3);
	}

	@Test (expected =  IndexOutOfBoundsException.class)
	public void addHorizontalWalThrowsIfBadArgsAreGiven()
	{
		mapMan.addHorizontalWall(14, 100, 2);
	}

	public void addVerticalWallAddsWalls()
	{
		//Arrange
		Map map = new Map(5, 5);

		// from, to, column (both including)
		//Act
		mapMan.addVerticalWall(-2, 0, 0);

		//Assert
		assertTrue(map.getTile(0, 0).isWall());
	}

	@Test
	public void addHorizontalWallWorksInEdges()
	{
		//Arrange
		Map map = new Map(5, 5);

		//Act
		mapMan.addHorizontalWall(-2, 2, -2);
		map = mapMan.getMap();

		//Assert
		assertTrue(map.getTile(2, -2).isWall());
	}

	@Test
	public void addVerticalWallWorksInEdges()
	{
		//Arrange
		Map map = new Map(5, 5);

		//Act
		mapMan.addVerticalWall(-2, 2, -2);
		map = mapMan.getMap();

		//Assert
		assertTrue(map.getTile(-2, 0).isWall());
	}

	@Test (expected =  IllegalArgumentException.class)
	public void addVerticalWallThrowsIfFromLargerThanTo()
	{
		mapMan.addVerticalWall(5, -2, 3);
	}

	@Test (expected =  IndexOutOfBoundsException.class)
	public void addVerticalWallThrowsIfBadArgsAreGiven()
	{
		mapMan.addVerticalWall(-75, -4, -10);
	}

	@Test
	public void partitionGivesTilesRooms()
	{
		// Act
		mapMan.partition();
		oddMapMan.partition();

		assertNotEquals(mapMan.getRoom(18, 5), 0);
		assertNotEquals(oddMapMan.getRoom(18, 5), 0);

	}

	@Test
	public void unpartitionedMapsHaveNoRooms()
	{
		assertEquals(mapMan.getRoom(4,3), 0);
		assertEquals(oddMapMan.getRoom(4, 3), 0);
	}

	@Test
	public void partitionGivesAlmostAllRoomNumbers()
	{
		//Act
		mapMan.partition();

		//Assert
		for(int column = -24; column < 25; column++)
		{
			for (int row = -25; row < 24; row++)
			{
				assertNotEquals(mapMan.getRoom(column, row), 0);
			}
		}
	}

	@Test
	public void partitionGivesAlmostAllRoomNumbersInOddMaps()
	{
		//Act
		oddMapMan.partition();

		//Assert
		for(int column = -24; column < 26; column++)
		{
			for (int row = -25; row < 25; row++)
			{
				assertNotEquals(oddMapMan.getRoom(column, row), 0);
			}
		}
	}


	@Test
	public void partitionLeavesTheFirstColumnAndRowBlank()
	{
		//Act
		mapMan.partition();

		//Assert

		for(int column = -25; column < 25; column++)
		{
			assertEquals(mapMan.getRoom(column, 24), 0);
		}

		for(int row = -25; row < 25; row++)
		{
			assertEquals(mapMan.getRoom(-25, row), 0);
		}

	}

	@Test
	public void partitionAddsWallInTopLeftCorner()
	{
		//Act
		mapMan.partition();
		oddMapMan.partition();

		//Assert
		assertEquals(true, map.getTile(-25, 24).isWall() );
		assertEquals(true, oddMap.getTile(-25, 25).isWall() );
	}

	@Test
	public void partitionAddsWallInBottomRightCorner()
	{
		//Act
		oddMapMan.partition();
		mapMan.partition();

		//Assert
		assertEquals(true, map.getTile(24, -25).isWall() );
		assertEquals(true, oddMap.getTile(25, -25).isWall() );

	}

	@Test
	public void roomStringHasZeros()
	{
		map = new Map(5, 5);
		assertTrue(mapMan.roomString().startsWith("0") );
	}

}
