public abstract class Enemy extends Movable {

    public Enemy(int x, int y) {
        super(x,y);
    }

    public abstract void act(int playerX, int playerY, Map map);
}
