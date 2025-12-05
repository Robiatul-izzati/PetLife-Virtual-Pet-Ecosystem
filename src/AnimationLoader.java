import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class AnimationLoader {

    public static ImageIcon load(String path) {
        if (path == null || path.isEmpty()) return placeholder();

        ImageIcon ic = loadFromResource(path.startsWith("/") ? path : "/" + path);
        if (ic != null) return ic;

        File f = new File(path);
        if (f.exists()) return new ImageIcon(path);

        return placeholder();
    }

    public static ImageIcon loadResource(String resourcePath) {
        if (resourcePath == null) return placeholder();
        return load(resourcePath);
    }

    private static ImageIcon loadFromResource(String resourcePath) {
        try {
            URL url = AnimationLoader.class.getResource(resourcePath);
            if (url != null) return new ImageIcon(url);
        } catch (Exception ignored) {}
        return null;
    }

    private static ImageIcon placeholder() {
        BufferedImage img = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0,0,200,200);
        g.setColor(Color.BLACK);
        g.drawString("No Img", 80, 100);
        g.dispose();
        return new ImageIcon(img);
    }
}
