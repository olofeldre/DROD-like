import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * The main class for the application.
 */
public class Game extends JPanel {
	public static final int WINDOW_WIDTH = 500;
	public static final int WINDOW_HEIGHT = 500;
	public static final String WINDOW_TITLE = "Rogue";

	private JFrame frame;
	private Map map;
	private Player player;
	private Keyboard keyboard;

    public static void main(String[] args)
	{
		Map testMap = testMap();

		JFrame gameFrame = FrameFactory.create(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
		Game game = new Game(gameFrame, testMap);
		Keyboard keyboard = new Keyboard(game);
		gameFrame.addKeyListener(keyboard);

		game.setPlayer((Player) testMap.getTile(2, -3).getMovable());
		game.start();


	}

	public static Map testMap() {
    	Map map = new Map(10, 10);
    	map.addHorizontalWall(-5, 4, 4);
    	map.addHorizontalWall(-5, 4, -5);
    	map.addVerticalWall(-5 ,4, -5);
		map.addVerticalWall(-5 ,4, 4);

		map.addVerticalWall(2, 3, -2);
    	map.addVerticalWall(-4, -2, -2);
    	map.addHorizontalWall(0, 1, 0);
    	map.addVerticalWall(-4, -2, 1);

    	map.createMovable(2, -3, MovableType.PLAYER);
    	map.createMovable(2,2, MovableType.ROACH);
		map.createMovable(-4,-3, MovableType.ROACH);
		map.createMovable(-3,-4, MovableType.ROACH);

    	return map;
	}

	/**
	 * Add the game to the frame so that it can be rendered to.
	 * @param frame
	 * @param map
	 */
	public Game(JFrame frame, Map map)
	{
		this.frame = frame;
		this.map = map;
		frame.add(this);

		loadResources();
	}

	private void loadResources() {
		Resource.loadImage("resources/images/player-sprite.png", "player");
		Resource.loadImage("resources/images/roach-sprite.png", "roach");
		Resource.loadImage("resources/images/sword-sprite.png", "sword");
	}

	/**
	 * Make the window visible.
	 */
	public void start()
	{
    	frame.setVisible(true);
    	repaint();
	}

	/**
	 * Paint the map.
	 * @param g
	 */
	public void paintComponent(Graphics g)
	{
        map.draw(g, getWidth(), getHeight());
	}

	public void setPlayer(Player player)
	{
		this.player = player;
	}

	/**
	 * Move the player in the appropriate direction and update all enemies.
	 * @param keyCode
	 */
	public void keyPressed(int keyCode) {
		switch(keyCode) {
            case KeyEvent.VK_8: player.move(Direction.UP, map); break;
            case KeyEvent.VK_9: player.move(Direction.UPRIGHT, map); break;
            case KeyEvent.VK_O: player.move(Direction.RIGHT, map); break;
            case KeyEvent.VK_K: player.move(Direction.DOWN, map); break;
            case KeyEvent.VK_L: player.move(Direction.DOWNRIGHT, map); break;
            case KeyEvent.VK_U: player.move(Direction.LEFT, map); break;
            case KeyEvent.VK_7: player.move(Direction.UPLEFT, map); break;
            case KeyEvent.VK_J: player.move(Direction.DOWNLEFT, map); break;

			//case KeyEvent.VK_Q: player.move(Direction.DOWNLEFT, map); break;
			//case KeyEvent.VK_W: player.move(Direction.DOWNLEFT, map); break;

		}

		map.updateEnemies(player.x, player.y);
		repaint();
	}
}
