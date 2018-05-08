import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class RoachTest {
    private Map map;
    private Roach roach;
    private final int playerStartX = 2;
    private final int playerStartY = -3;
    private final int NO_MOVEMENT = 0;

    @Before
    public void setup() {
        map = Game.testMap();
        roach = (Roach) map.getTile(2, 2).getMovable();
    }

    @Test
    public void playerStandingStillShouldMakeRoachMoveDownOnce() {
        assertRoachMovesWhenPlayerIsAt(NO_MOVEMENT, -1, playerStartX, playerStartY);
    }


    public void assertRoachMovesWhenPlayerIsAt(int xStep, int yStep, int playerX, int playerY) {
        int startX = roach.x;
        int startY = roach.y;

        map.updateEnemies(playerX, playerY);

        assertThat(roach.x, equalTo(startX + xStep));
        assertThat(roach.y, equalTo(startY + yStep));
    }
}