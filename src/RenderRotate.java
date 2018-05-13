import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RenderRotate {

    public static void renderSprite(Graphics graphics, int x, int y, double angle, BufferedImage sprite) {

        Graphics2D g = (Graphics2D) graphics;
        AffineTransform transform = g.getTransform();

        g.setColor(Color.BLUE);
        g.fillRect(x, y, 2, 2);

        g.rotate(angle, x, y);
        g.drawImage(sprite, x - sprite.getWidth() / 2,  y - sprite.getHeight() / 2, null);
        g.setTransform(transform);
    }
}
