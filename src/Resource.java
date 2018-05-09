import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Resource {
    public static HashMap<String, BufferedImage> images = new HashMap<>();

    public static void loadImage(String path, String name) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            images.put(name, image);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage getImage(String name) {
        return images.get(name);
    }
}
