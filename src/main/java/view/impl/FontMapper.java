package view.impl;

import shared.TextType;

import java.awt.*;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class FontMapper {
    private final Map<TextType, Font> fonts = new HashMap<>();
    Path fontsPath;

    public FontMapper(Path fontsPath) {
        if (fontsPath == null) {
            throw new IllegalArgumentException("fontsPath cannot be null");
        }
        this.fontsPath = fontsPath;
        loadFont(TextType.DEFAULT, Path.of("roboto_condensed/static/RobotoCondensed-Bold.ttf"));
        loadFont(TextType.CAPTION, Path.of("miroslav/miroslav_bold.ttf"));
    }

    private void loadFont(TextType textType, Path path) {
        if (path == null) return;

        Path fullPath = fontsPath.resolve(path);

        try {
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File(fullPath.toUri()));
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(font);
            fonts.put(textType, font);
        } catch (Exception e) {
            System.err.println("Ошибка загрузки шрифта " + path + ": " + e.getMessage());
            fonts.put(textType, new Font("SansSerif", Font.PLAIN, 12));
        }
    }

    Font getFont(TextType textType) {
        return fonts.getOrDefault(textType, fonts.get(TextType.DEFAULT));
    }
}
