import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class RenderRotate {

    public static void renderSprite(Graphics g, int centerX, int centerY, double angle, BufferedImage sprite) {

        Graphics2D gr = (Graphics2D) g;
        AffineTransform transform = gr.getTransform();

        gr.translate(centerX, centerY);
        gr.rotate(angle);
        gr.translate(-centerX, -centerY);

        gr.drawImage(sprite,  centerX - sprite.getWidth() / 2, centerY - sprite.getHeight() / 2, null);

        gr.setTransform(transform);
    }

}
