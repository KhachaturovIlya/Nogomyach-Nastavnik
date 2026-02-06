package view.impl;

import javax.imageio.IIOException;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ImgMapper {
    private final Map<String, BufferedImage> images = new HashMap<>();
    private final Map<String, Path> imgPaths;
    private final Path resourcePath;

    public ImgMapper(Path resourcePath, Map<String, Path> imgPaths) {
        this.resourcePath = resourcePath;
        this.imgPaths = imgPaths;
    }

    public void loadImages(String name) {
        if (images.containsKey(name)) return;

        Path path = resourcePath.resolve(imgPaths.get(name));

        try {
            BufferedImage image = ImageIO.read(new File(path.toUri()));
            images.put(name, image);
        } catch (IOException exception) {
            System.err.println("Failed to load image " + name);
        }
    }

    public BufferedImage getImage(String name) {
        return images.get(name);
    }
}
