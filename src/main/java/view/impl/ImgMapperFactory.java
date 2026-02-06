package view.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImgMapperFactory {
    private final Path resourcesPath;
    private final ObjectMapper objectMapper;

    public ImgMapperFactory(Path resourcesPath) {
        this.resourcesPath = resourcesPath;
        this.objectMapper = new ObjectMapper();
    }

    public ImgMapper construct(String configPath) {
        File mapFile = resourcesPath.resolve(configPath + ".json").toFile();

        Map<String, Path> imgPaths = new HashMap<>();

        try {
            imgPaths = objectMapper.readValue(
                mapFile,
                new TypeReference<Map<String, Path>>() {
                }
            );
        } catch (IOException e) {
            System.err.println("Error reading file: " + mapFile.getAbsolutePath() + ".json");
        }

        return new ImgMapper(resourcesPath, imgPaths);
    }
}
