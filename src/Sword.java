import java.awt.*;
import java.awt.image.BufferedImage;

public class Sword extends Movable {
    public Sword(int x, int y) {
        super(x, y);
        this.solid = false;
    }

    @Override
    public void display(Graphics graphics, int x, int y) {
        BufferedImage sprite = Resource.getImage("sword");
        graphics.drawImage(sprite, x - sprite.getWidth(), y - sprite.getHeight(), null);
    }

}
