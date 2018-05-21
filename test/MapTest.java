import org.junit.Before;
import org.junit.Test;


import java.awt.*;

import static org.hamcrest.core.IsEqual.equalTo;
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
	public void isWalkableShouldReturnTrueForAllTilesInEmptyMap() {
		map = new Map(10, 10);
		assertTrue(map.isWalkable(0, 0));
		assertTrue(map.isWalkable(0, 1));
		assertTrue(map.isWalkable(4, 4));
	}

	@Test
	public void isWalkableShouldReturnFalseOnWall() {
		map = new Map(10, 10);
		Cartographer smallMapMan = new Cartographer(map, 10, 10);

		smallMapMan.addVerticalWall(-5, 4, -5);

		assertFalse(map.isWalkable(-5, -5));
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void isWalkableWithPositionOutsideBoundsShouldThrowException() {
    	map.isWalkable(100, 100);
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void getTileCenterShouldThrowExceptionGivenPositionOutsideMap() {
    	map = new Map(10, 10);
    	map.getTileCenter(-20, -20, 500 ,500);
	}

	@Test
	public void getTileCenterShouldBeCorrectOnTopLeftCorner() {
    	map = new Map(10, 10);
    	Point center = map.getTileCenter(-5, 4, 500, 500);
    	assertThat((int)center.getX(), equalTo(25));
    	assertThat((int)center.getY(), equalTo(25));
	}

	@Test
	public void getTileCenterShouldBeCorrectOnCenterTile() {
    	map = new Map(10, 10);
    	Point center = map.getTileCenter(0, 0, 500, 500);
    	assertThat((int) center.getX(), equalTo(275));
    	assertThat((int) center.getY(), equalTo(225));
	}

	@Test
	public void createPlayerShouldReturnValidPlayer() {
    	assertThat(map.createMovable(0,0, MovableType.PLAYER), notNullValue());
	}

	@Test(expected=IndexOutOfBoundsException.class)
	public void createPlayerShouldThrowExceptionIfTileIsOutsideBounds() {
    	map.createMovable(50, 50, MovableType.PLAYER);
	}

	@Test
	public void shouldAddPlayerToTileAtCorrectPosition() {
    	map.createMovable(10, 10, MovableType.PLAYER);
    	Tile tile = map.getTile(10 ,10);
    	Movable movable = tile.getMovable();
    	assertThat(movable.getType(), equalTo(MovableType.PLAYER));
	}

	@Test
	public void shouldAddRoachAtCorrectPosition() {
		map.createMovable(8, 8, MovableType.ROACH);
		Tile tile = map.getTile(8 ,8);
		Movable movable = tile.getMovable();
		assertThat(movable.getType(), equalTo(MovableType.ROACH));
	}

	@Test
	public void updateMovablesWorksInTestMap()
	{
		//Arrange
		Map test = MapGenerator.testMap();

		//Act
		test.updateEnemies(2, -3);

		//Assert
		assertEquals(test.getTile(-3, -4).getMovable().getType(),
				MovableType.ROACH);
		assertEquals(test.getTile(-3, -3).getMovable().getType(),
				MovableType.ROACH);
		assertEquals(test.getTile(2, 1).getMovable().getType(),
				MovableType.ROACH);


	}


}