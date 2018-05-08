import java.awt.*;
import java.awt.geom.Point2D;

public abstract class Movable {
    protected MovableType type;
    protected int x;
    protected int y;

    public Movable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected MovableType getType() {
        return type;
    }

    public void move(Direction direction, Map map) {

    }

    public abstract void display(Graphics graphics, int x, int y);

}
