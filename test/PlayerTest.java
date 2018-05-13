import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private Map map;

    @Before
    public void setup() {
        map = MapGenerator.testMap();
        player = (Player) map.getTile(2, -3).getMovable();
    }

    @Test
    public void playerShouldIncrementXWhenMovingToTheRight() {
        int previousX = player.x;
        int previousY = player.y;

        player.move(Direction.RIGHT, map);

        assertThat(player.x, equalTo(previousX + 1));
        assertThat(player.y, equalTo(previousY));
    }

    @Test
    public void rotateShouldChangeSwordPos()
	{
		player.rotate(Direction.RIGHT, map);
		System.out.println(map.getMovable(3, -2));
		assertNotNull(map.getTile(3, -4).getMovable());

	}

	@Test
	public void RotateLeftWorksNicely()
	{
		player.rotate(Direction.LEFT, map);
		assertNotNull(map.getTile(3, -2).getMovable());
	}


}