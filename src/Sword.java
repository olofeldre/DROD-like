import java.awt.*;
import java.awt.image.BufferedImage;

public class Sword extends Movable {
    private int angle = 0;

    public Sword(int x, int y) {
        super(x, y);
        this.type = MovableType.SWORD;
        this.solid = false;
    }

    @Override
    public void display(Graphics graphics, int x, int y) {
        BufferedImage sprite = Resource.getImage("sword");
        graphics.setColor(Color.RED);
//      graphics.fillRect(x,y , 5, 5);

        RenderRotate.renderSprite(graphics, x, y, -Math.toRadians(angle), sprite);
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

}
