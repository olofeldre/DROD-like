public abstract class Enemy extends Movable {
    protected boolean alive = true;

    public Enemy(int x, int y) {
        super(x,y);
    }

    public abstract void act(int playerX, int playerY, Map map);

    public boolean isAlive() {
        return alive;
    }

    protected void kill() {
        System.out.println("Enemy killed");
        this.alive = false;
    }

}
