import org.junit.Before;
import org.junit.Test;

import javax.swing.*;

import java.awt.event.KeyEvent;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class GameTest {
    private Game game;
    private Player player;
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
        int previousX = player.x;
        int previousY = player.y;
        game.keyPressed(KeyEvent.VK_I);

        assertThat(player.x, equalTo(previousX));
        assertThat(player.y, equalTo(previousY));
    }

    @Test
    public void typingLShouldMoveThePlayerOneTileToTheRight() {
        int previousX = player.x;
        int previousY = player.y;

        game.keyPressed(KeyEvent.VK_O);

        assertThat(player.x, equalTo(previousX + 1));
        assertThat(player.y, equalTo(previousY));
    }
}