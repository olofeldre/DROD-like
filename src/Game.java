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
	private static final Font smallText = new Font("arial", Font.PLAIN, 25);
	private static final Font largeText = new Font("arial", Font.PLAIN, 50);

	private JFrame frame;
	private Map map;
	private Player player;

    public static void main(String[] args)
	{
<<<<<<< HEAD
		Map map = new Map(50, 50);
		map.partition();

		//JFrame gameFrame = FrameFactory.create(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
		//Game game = new Game(gameFrame, map);
		//Keyboard keyboard = new Keyboard(game);
		//gameFrame.addKeyListener(keyboard);

		//game.setPlayer((Player) map.getTile(2, -3).getMovable());
		//game.start();
		System.out.println(map);

=======
		Map testMap = MapGenerator.testMap();

		JFrame gameFrame = FrameFactory.create(WINDOW_TITLE, WINDOW_WIDTH, WINDOW_HEIGHT);
		Game game = new Game(gameFrame, testMap);
		Keyboard keyboard = new Keyboard(game);
		gameFrame.addKeyListener(keyboard);

		game.setPlayer((Player) testMap.getMovable(2, -3));
		game.start();
>>>>>>> fbb9dc6fccd152ebc0cc912f16e84ba0314e0640
	}

	public void build() {
    	map = MapGenerator.testMap();
		setPlayer((Player) map.getMovable(2, -3));
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
		if(player.alive) {
			map.draw(g, getWidth(), getHeight());
		}
		else {
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, getWidth(), getHeight());
			g.setColor(Color.WHITE);
			g.setFont(largeText);
			g.drawString("You died", getWidth()/2 - 100, getHeight()/2);

			g.setFont(smallText);
			g.drawString("Press R to try again", getWidth()/2 - 100, getHeight()/2 + 100);

		}
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
		boolean update = true;
		switch(keyCode) {
            case KeyEvent.VK_8: player.move(Direction.UP, map); break;
            case KeyEvent.VK_9: player.move(Direction.UPRIGHT, map); break;
            case KeyEvent.VK_O: player.move(Direction.RIGHT, map); break;
            case KeyEvent.VK_K: player.move(Direction.DOWN, map); break;
            case KeyEvent.VK_L: player.move(Direction.DOWNRIGHT, map); break;
            case KeyEvent.VK_U: player.move(Direction.LEFT, map); break;
            case KeyEvent.VK_7: player.move(Direction.UPLEFT, map); break;
            case KeyEvent.VK_J: player.move(Direction.DOWNLEFT, map); break;

			case KeyEvent.VK_NUMPAD8: player.move(Direction.UP, map); break;
			case KeyEvent.VK_NUMPAD9: player.move(Direction.UPRIGHT, map); break;
			case KeyEvent.VK_NUMPAD6: player.move(Direction.RIGHT, map); break;
			case KeyEvent.VK_NUMPAD2: player.move(Direction.DOWN, map); break;
			case KeyEvent.VK_NUMPAD3: player.move(Direction.DOWNRIGHT, map); break;
			case KeyEvent.VK_NUMPAD4: player.move(Direction.LEFT, map); break;
			case KeyEvent.VK_NUMPAD7: player.move(Direction.UPLEFT, map); break;
			case KeyEvent.VK_NUMPAD1: player.move(Direction.DOWNLEFT, map); break;

			case KeyEvent.VK_Q: player.rotate(Direction.LEFT, map); break;
			case KeyEvent.VK_W: player.rotate(Direction.RIGHT, map); break;
			case KeyEvent.VK_I: break;
			case KeyEvent.VK_NUMPAD5: break;
			case KeyEvent.VK_R: build();
			default: update = false;

		}

		if(update) {
			map.updateEnemies(player.x, player.y);
		}

		repaint();
	}
}
