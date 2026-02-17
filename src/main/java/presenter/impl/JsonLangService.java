package presenter.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import presenter.ILangService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Slf4j
public class JsonLangService implements ILangService {
    private final Map<String, String> textMap = new HashMap<>();
    private final Path resourcesRootPath;
    private final ObjectMapper mapper;

    public JsonLangService(Path resourcesRootPath) {
        this.resourcesRootPath = resourcesRootPath;
        this.mapper = new ObjectMapper();
    }

    @Override
    public String getText(String textId) {
        log.trace("Parsing text with id {}.", textId);
        if (textId.equals("NO_TEXT")) {
            return null;
        }

        return textMap.getOrDefault(textId, textId);
    }

    @Override
    public void loadFile(Path path) throws IOException {
        Path filePath = resourcesRootPath.resolve(path.toString() + ".json");

        if (Files.notExists(filePath)) {
            log.debug("Localization file not found: {}.", filePath.toAbsolutePath());
        }

        log.info("Loading localization file from {}...", filePath.toAbsolutePath());

        Map<String, String> loadedText = mapper.readValue(filePath.toFile(), new TypeReference<>(){});

        textMap.putAll(loadedText);
    }

    @Override
    public void resetMap() {
        log.info("Full reset of localization map.");
        textMap.clear();
    }
}
