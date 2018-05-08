import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.event.KeyEvent;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Player player;
    private final int NO_MOVEMENT = 0;

    @Before
    public void setup() {
        JFrame frame = FrameFactory.create("Title", 500, 500);
        Map map = Game.testMap();
        game = new Game(frame, map);
        player = (Player) map.getTile(2, -3).getMovable();
        game.setPlayer(player);
    }

    @Test
    public void typingIShouldNotMovePlayer() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_I, NO_MOVEMENT, NO_MOVEMENT);
    }

    @Test
    public void pressingOShouldMoveThePlayerOneTileToTheRight() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_O, 1, NO_MOVEMENT);
    }

    @Test
    public void pressing8ShouldMoveThePlayerUpOneTile() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_8, NO_MOVEMENT, 1);
    }

    @Test
    public void pressingUShouldNotAllowPlayerToMoveThroughWall() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_U, NO_MOVEMENT, NO_MOVEMENT);
    }

    @Test
    public void pressingJShouldNotAllowPlayerToMoveThroughWall() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_J, NO_MOVEMENT, NO_MOVEMENT);
    }

    @Test
    public void pressing9ShouldMoveThePlayerTopRight() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_9, 1, 1);
    }

    @Test
    public void pressingKShouldMoveThePlayerDown() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_K, NO_MOVEMENT, -1);
    }

    @Test
    public void pressingLShouldMovePlayerDownRight() {
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_L, 1, -1);
    }

    @Test
    public void movingRightThenPressingUShouldAlllowPlayerToMoveLeft() {
        game.keyPressed(KeyEvent.VK_O);
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_U, -1, NO_MOVEMENT);
    }

    @Test
    public void movingRightThenPressing7ShouldAllowPlayerToMoveTopLeft() {
        game.keyPressed(KeyEvent.VK_O);
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_7, -1, 1);
    }

    @Test
    public void movingRightThenPressingJShouldAllowPlayerToMoveBottomRight() {
        game.keyPressed(KeyEvent.VK_O);
        assertPlayerMovesWhenPressingKey(KeyEvent.VK_J, -1, -1);
    }

    private void assertPlayerMovesWhenPressingKey(int key, int xStep, int yStep) {
        int previousX = player.x;
        int previousY = player.y;

        game.keyPressed(key);

        assertThat(player.x, equalTo(previousX + xStep));
        assertThat(player.y, equalTo(previousY + yStep));
    }
}