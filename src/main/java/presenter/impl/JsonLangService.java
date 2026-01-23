package presenter.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import presenter.ILangService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class JsonLangService implements ILangService {
    private final Map<String, String> textMap = new HashMap<>();
    private final Path resourcesRootPath;

    public JsonLangService(Path resourcesRootPath) {
        this.resourcesRootPath = resourcesRootPath;
    }

    @Override
    public String getText(String textId) {
        if (textId.equals("NO_TEXT")) {
            return null;
        }

        String text = textMap.get(textId);
        if (text == null) {
            return textId;
        }
        return text;
    }

    @Override
    public void loadFile(Path path) throws IOException {
        File file = resourcesRootPath.resolve(path.toString() + ".json").toFile();

        if (!file.exists()) {
            throw new IOException("File not found: " + file.getAbsolutePath());
        }

        ObjectMapper mapper = new ObjectMapper();

        Map<String, String> loadedText = mapper.readValue(file, new TypeReference<Map<String, String>>() {});

        textMap.putAll(loadedText);
    }

    @Override
    public void resetMap() {
        textMap.clear();
    }
}
