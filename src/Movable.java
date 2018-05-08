import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Movable {
    protected MovableType type;
    protected int x;
    protected int y;
    protected boolean solid = true;

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

        return tryMoveTo(newX, newY, map);
    }

    public boolean tryMoveTo(int x, int y, Map map) {
        System.out.println(tileFree(x, y, map));
        System.out.println(solid);
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
