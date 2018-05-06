import javax.swing.*;
import java.awt.*;

/**
 * The main class for running the game.
 */
public class Game extends JPanel {
    private JFrame frame;
    private Map map;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rogue");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLocationRelativeTo(null);

        Map map = new Map(50 ,50);
        map.addHorizontalWall(-25, 24, -25);
        map.addHorizontalWall(-25, 24, 24);
        map.addVerticalWall(-25, 24, -25);
        map.addVerticalWall(-25, 24, 24);
        map.partition();
        Game game = new Game(frame, map);
        game.start();
    }

    /**
     * Add this instance of game to the JFrame so it can be drawn to.
     * @param frame
     */
    public Game(JFrame frame, Map map) {
        this.frame = frame;
        this.map = map;
        frame.add(this);
    }

    public void start() {
        frame.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        map.draw(g, getWidth(), getHeight());
    }
}
