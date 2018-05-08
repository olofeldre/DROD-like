import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Movable {
    protected MovableType type;
    protected int x;
    protected int y;
    protected boolean solid = true;
    protected Direction direction = Direction.RIGHT;

    public Movable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected MovableType getType() {
        return type;
    }

    public boolean move(Direction direction, Map map) {
        int newX = x;
        int newY = y;

        switch(direction) {
            case LEFT: newX -= 1; break;
            case RIGHT: newX += 1; break;
            case UP: newY += 1; break;
            case DOWN: newY -= 1; break;
            case UPLEFT: newX -= 1; newY += 1; break;
            case UPRIGHT: newX += 1; newY += 1; break;
            case DOWNLEFT: newX -= 1; newY -= 1; break;
            case DOWNRIGHT: newX += 1; newY -= 1; break;
        }

        if(tryMoveTo(newX, newY, map)) {
            this.direction = direction;
            return true;
        }
        return false;
    }

    public boolean tryMoveTo(int x, int y, Map map) {
        if(tileFree(x, y, map) || !solid) {
            map.getTile(this.x, this.y).removeMovable();
            this.x = x;
            this.y = y;
            map.getTile(x, y).setMovable(this);
            return true;
        }

        return false;
    }

    private boolean tileFree(int x, int y, Map map) {
        if (map.getTile(x, y).isWall() || map.getTile(x, y).getMovable() != null)
        {
            return false;
        }

        return true;
    }

    public abstract void display(Graphics graphics, int x, int y);

}
