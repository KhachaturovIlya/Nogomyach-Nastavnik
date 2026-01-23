package presenter.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import presenter.impl.interfaces.IWidgetFileFactory;
import presenter.impl.widget.Button;
import presenter.impl.widget.Container;
import presenter.impl.widget.Label;
import presenter.impl.widget.Widget;
import shared.Color;
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

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = Button.class, name = "button"),
        @JsonSubTypes.Type(value = Label.class, name = "label"),
        @JsonSubTypes.Type(value = Container.class, name = "container")
    })
    public static abstract class WidgetMixin {
        @JsonCreator
        WidgetMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("textId") String textId,
            @JsonProperty("textColor") Color textColor,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition
        ) {}
    }

    public abstract static class ContainerMixin {
        @JsonCreator
        ContainerMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("textId") String textId,
            @JsonProperty("textColor") Color textColor,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition,
            @JsonProperty("children") List<Widget> children
        ) {}
    }

    public abstract static class ButtonMixin {
        @JsonCreator
        ButtonMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("textId") String textId,
            @JsonProperty("textColor") Color textColor,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition,
            @JsonProperty("clickActions") List<String> actions,
            @JsonProperty("actionsContext") List<String> context
        ) {}
    }

    public abstract static class LabelMixin {
        @JsonCreator
        LabelMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("textId") String textId,
            @JsonProperty("textColor") Color textColor,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition
        ) {}
    }

    public static abstract class Vector2Mixin {
        Vector2Mixin(@JsonProperty("x") double x, @JsonProperty("y") double y) {}
    }

    public JsonWidgetFactory(Path srcPath) {
        this.srcPath = srcPath;
        mapper = new ObjectMapper();

        this.mapper.registerModule(new ParameterNamesModule());

        this.mapper.addMixIn(Shape.class, ShapeMixin.class);
        this.mapper.addMixIn(Vector2.class, Vector2Mixin.class);
        this.mapper.addMixIn(Widget.class, WidgetMixin.class);
        this.mapper.addMixIn(Button.class, ButtonMixin.class);
        this.mapper.addMixIn(Label.class, LabelMixin.class);
        this.mapper.addMixIn(Container.class, ContainerMixin.class);
    }

    @Override
    public Map<Integer, Widget> construct(Path configPath) throws IOException {
        try {
            List<Widget> widgets = mapper.readValue(
                srcPath.resolve(configPath.toString() + ".json").toFile(),
                new TypeReference<List<Widget>>() {}
            );

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
}
