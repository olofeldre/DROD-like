import javax.swing.*;
import java.awt.*;

/**
 * The main class for the application.
 */
public class Game extends JPanel {
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;
	public static final String WINDOW_TITLE = "Rogue";

	private JFrame frame;
	private Map map;

    public static void main(String[] args) {
		Map testMap = new Map(20, 20);
		testMap.partition();
		System.out.println(testMap.roomString());
		System.out.println(testMap);

		JFrame gameFrame = FrameFactory.create(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);

		Game game = new Game(gameFrame, testMap);
		game.start();
	}

	/**
	 * Add the game to the frame so that it can be rendered to.
	 * @param frame
	 * @param map
	 */
	public Game(JFrame frame, Map map) {
		this.frame = frame;
		this.map = map;
		frame.add(this);
	}

	/**
	 * Make the window visible.
	 */
	public void start() {
    	frame.setVisible(true);
    	repaint();
	}

	/**
	 * Paint the map.
	 * @param g
	 */
	public void paintComponent(Graphics g) {
        map.draw(g, getWidth(), getHeight());
	}
}
