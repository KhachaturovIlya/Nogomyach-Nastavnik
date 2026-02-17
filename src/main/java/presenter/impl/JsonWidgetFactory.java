package presenter.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import presenter.impl.interfaces.ILayoutStrategy;
import presenter.impl.interfaces.IWidgetFileFactory;
import presenter.impl.widget.*;
import presenter.impl.widget.dinamicContainerPolicies.HorizontalLayout;
import presenter.impl.widget.dinamicContainerPolicies.VerticalLayout;
import shared.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class JsonWidgetFactory implements IWidgetFileFactory {
    final private Path srcPath;
    final private ObjectMapper mapper;

    public JsonWidgetFactory(Path srcPath) {
        this.srcPath = srcPath;

        mapper = new ObjectMapper();
        configureMapper();
    }

    public JsonWidgetFactory(Path srcPath, ObjectMapper mapper) {
        this.srcPath = srcPath;

        this.mapper = mapper;
        configureMapper();
    }

    private void configureMapper() {
        this.mapper.registerModule(new ParameterNamesModule());
        this.mapper.registerModule(new JsonMixinModule());
    }

    @Override
    public Map<Integer, Widget> construct(Path configPath) throws IOException {
        Path fullPath = srcPath.resolve(configPath.toString() + ".json");

        if (Files.notExists(fullPath)) {
            log.error("Could not find log file at: {}.", fullPath);
            throw new IOException("File not found: " + fullPath);
        }

        try {
            List<Widget> widgets = mapper.readValue(fullPath.toFile(), new TypeReference<>() {});

            log.info("Successfully parse widget config file.");

            return widgets.stream().collect(Collectors.toMap(Widget::getId, Function.identity()));
        } catch (IOException exception) {
            log.error("Could not parse widget config file at: {}", fullPath);
            throw exception;
        }
    }
}
