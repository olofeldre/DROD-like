import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class TileTest {
    private Tile emptyTile;
    private Tile fullTile;
    @Before
    public void setup() {
        emptyTile = new Tile(false);
        fullTile = new Tile(true);
    }

    @Test
    public void isWallShouldReturnTrueForFullTile() {
        assertThat(fullTile.isWall(), equalTo(true));
    }

    @Test
    public void setTypeShouldSetType() {
        emptyTile.setType(true);
        fullTile.setType(false);

        assertThat(emptyTile.isWall(), equalTo(true));
        assertThat(fullTile.isWall(), equalTo(false));
    }

    @Test
    public void setEmptyTileToSameTypeShouldDoNothing() {
        boolean type = emptyTile.isWall();

        emptyTile.setType(false);
        assertThat(emptyTile.isWall(), equalTo(type));
    }

    @Test
    public void setFullTileToSameTypeShouldDoNothing() {
        boolean type = fullTile.isWall();

        fullTile.setType(true);
        assertThat(fullTile.isWall(), equalTo(type));
    }

    @Test
    public void toStringShouldReturnCorrectStringOnEmpty() {
    	//Arrange
        String desiredString = "-";

        //Act
		String compareString = emptyTile.toString();

		//Assert
		assertEquals(desiredString, compareString);
    }

    public void toStringShouldReturnCorrectStringOnFull()
	{
		//Arrange
		String desiredString = "@";

		//Act
		String compareString = fullTile.toString();

		//Assert
		assertEquals(desiredString, compareString);
	}
}