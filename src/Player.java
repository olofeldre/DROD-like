import java.awt.*;

/**
 * The game's player. It should be able to draw itself and move to different tiles on the map.
 */
public class Player {
    private int x;
    private int y;

    public Player(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    /**
     * Draw this player.
     * @param g
     */
    public void draw(Graphics g, int windowWidth, int windowHeight, Map map)
    {
        g.setColor(new Color(0, 255, 150));

        Point position = map.getTileCenter(x, y, windowWidth, windowHeight);
        g.fillOval((int)position.getX() - 5, (int)position.getY() - 5, 10, 10);
    }
}
