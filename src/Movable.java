import java.awt.*;

public abstract class Movable {
    protected MovableType type;

    protected MovableType getType() {
        return type;
    }

    public abstract void display(Graphics graphics);

}
