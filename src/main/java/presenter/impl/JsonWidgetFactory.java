package presenter.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import presenter.impl.interfaces.IWidgetFileFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class JsonWidgetFactory implements IWidgetFileFactory {
    final private Path srcPath;
    final private ObjectMapper mapper;
    final static private AtomicInteger counter = new AtomicInteger(0);

    public JsonWidgetFactory(Path srcPath) {
        this.srcPath = srcPath;
        mapper = new ObjectMapper();
    }

    private List<Widget> construct(Path configPath, BiFunction<WidgetConfig, CommandLibrary, Widget> assembler,
        CommandLibrary commandLibrary) throws IOException {
        try {
            List<WidgetConfig> dtos = mapper.readValue(
                srcPath.resolve(configPath).toFile(),
                new TypeReference<List<WidgetConfig>>() {}
            );

            return dtos.stream().
                map(dto -> assembler.apply(dto, commandLibrary)).
                toList();
        } catch (IOException exception) {
            System.err.println("Could not read config file: " + exception.getMessage());
            throw new IOException(exception);
        }
    }

    private Widget assembleButton(WidgetConfig widgetConfig, CommandLibrary commandLibrary) {
        return Widget.createButton(
            counter.getAndIncrement(),
            widgetConfig.shape(),
            widgetConfig.shapeColor(),
            widgetConfig.position(),
            
            commandLibrary.getCommand(widgetConfig.actionName())
        );
    }

    @Override
    public List<Widget> constructButtons(Path configPath, CommandLibrary commandLibrary) throws IOException {
        return construct(configPath, this::assembleButton, commandLibrary);
    }

    private Widget assembleLabel(WidgetConfig widgetConfig, CommandLibrary commandLibrary) {
        return Widget.createLabel(
            widgetConfig.shape(),
            widgetConfig.shapeColor(),
            widgetConfig.text(),
            widgetConfig.textColor(),
            widgetConfig.position()
        );
    }

    @Override
    public List<Widget> constructLabels(Path configPath) throws IOException {
        return construct(configPath, this::assembleLabel, null);
    }
}
