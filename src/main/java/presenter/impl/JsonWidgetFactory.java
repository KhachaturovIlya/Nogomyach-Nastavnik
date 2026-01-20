package presenter.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import presenter.impl.interfaces.IWidgetFileFactory;
import shared.Shape;
import shared.Vector2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;

public class JsonWidgetFactory implements IWidgetFileFactory {
    final private Path srcPath;
    final private ObjectMapper mapper;
    final static private AtomicInteger counter = new AtomicInteger(0);

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Shape.Rectangle.class, name = "rectangle"),
        @JsonSubTypes.Type(value = Shape.Circle.class, name = "circle"),
        @JsonSubTypes.Type(value = Shape.Square.class, name = "square")
    })
    public static abstract class ShapeMixin {}

    public static abstract class Vector2Mixin {
        Vector2Mixin(@JsonProperty("x") double x, @JsonProperty("y") double y) {}
    }

    public JsonWidgetFactory(Path srcPath) {
        this.srcPath = srcPath;
        mapper = new ObjectMapper();

        this.mapper.registerModule(new ParameterNamesModule());

        this.mapper.addMixIn(Shape.class, ShapeMixin.class);
        this.mapper.addMixIn(Vector2.class, Vector2Mixin.class);
    }

    private Map<Integer, Widget> construct(Path configPath, BiFunction<WidgetConfig, CommandLibrary, Widget> assembler,
        CommandLibrary commandLibrary) throws IOException {
        try {
            List<WidgetConfig> dtos = mapper.readValue(
                srcPath.resolve(configPath).toFile(),
                new TypeReference<List<WidgetConfig>>() {}
            );

            List<Widget> widgets =  dtos.stream().
                map(dto -> assembler.apply(dto, commandLibrary)).
                toList();

            Map<Integer, Widget> mappedWidgets = new HashMap<>();
            for (Widget widget : widgets) {
                mappedWidgets.put(widget.getId(), widget);
            }
            return mappedWidgets;
        } catch (IOException exception) {
            System.err.println("Could not read config file: " + exception.getMessage());
            throw new IOException(exception);
        }
    }

    private Widget assembleButton(WidgetConfig widgetConfig, CommandLibrary commandLibrary) {
        return Widget.createButton(
            counter.getAndIncrement(),
            widgetConfig.active(),
            widgetConfig.shape(),
            widgetConfig.shapeColor(),

            widgetConfig.position(),

            widgetConfig.text(),
            widgetConfig.textColor(),
            
            commandLibrary.getCommand(widgetConfig.actionName())
        );
    }

    @Override
    public Map<Integer, Widget> constructButtons(Path configPath, CommandLibrary commandLibrary) throws IOException {
        return construct(configPath, this::assembleButton, commandLibrary);
    }

    private Map<Integer, Widget> getChildrenLabels(List<WidgetConfig> labels, CommandLibrary commandLibrary) throws IOException {
        if (labels.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Integer, Widget> childrenLabels = new HashMap<>();
        for (WidgetConfig widgetConfig : labels) {
            Widget newLabel = assembleLabel(widgetConfig, commandLibrary);
            childrenLabels.put(newLabel.getId(), newLabel);
        }
        return childrenLabels;
    }

    private Map<Integer, Widget> getChildrenButtons(List<WidgetConfig> buttons, CommandLibrary commandLibrary) throws IOException {
        if (buttons.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<Integer, Widget> childrenButtons = new HashMap<>();
        for (WidgetConfig widgetConfig : buttons) {
            Widget newLabel = assembleButton(widgetConfig, commandLibrary);
            childrenButtons.put(newLabel.getId(), newLabel);
        }
        return childrenButtons;
    }

    private Widget assembleLabel(WidgetConfig widgetConfig, CommandLibrary commandLibrary) throws RuntimeException {
        try {
            return Widget.createLabel(
                counter.getAndIncrement(),
                widgetConfig.active(),
                widgetConfig.shape(),
                widgetConfig.shapeColor(),
                widgetConfig.text(),
                widgetConfig.textColor(),
                widgetConfig.position(),
                getChildrenLabels(widgetConfig.subLabels(), commandLibrary),
                getChildrenButtons(widgetConfig.subButtons(), commandLibrary),
                null
            );
        } catch (IOException exception) {
            System.err.println("Could not read config file: " + exception.getMessage());
            throw new RuntimeException(exception);
        }
    }

    @Override
    public Map<Integer, Widget> constructLabels(Path configPath, CommandLibrary commandLibrary) throws IOException {
        return construct(configPath, this::assembleLabel, commandLibrary);
    }
}
