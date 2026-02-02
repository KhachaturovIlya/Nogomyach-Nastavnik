package presenter.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
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

public class JsonWidgetFactory implements IWidgetFileFactory {
    final private Path srcPath;
    final private ObjectMapper mapper;

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
        @JsonSubTypes.Type(value = Container.class, name = "container"),
        @JsonSubTypes.Type(value = DynamicContainer.class, name = "dynamic_container")
    })
    public static abstract class WidgetMixin {}

    public abstract static class ContainerMixin {
        @JsonCreator
        ContainerMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("img") List<String> img,
            @JsonProperty("textConfig") TextConfig textConfig,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition,
            @JsonProperty("children") List<Widget> children
        ) {}
    }

    public abstract static class DynamicContainerMixin {
        @JsonCreator
        DynamicContainerMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("img") List<String> img,
            @JsonProperty("textConfig") TextConfig textConfig,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition,
            @JsonProperty("layoutStrategy") ILayoutStrategy layoutStrategy,
            @JsonProperty("template") Widget template,
            @JsonProperty("templatesInfo") DataBinding templatesInfo
        ) {}
    }

    @JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type"
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = HorizontalLayout.class, name = "horizontal_layout"),
        @JsonSubTypes.Type(value = VerticalLayout.class, name = "vertical_layout")
    })
    public static abstract class LayoutMixin {}

    public abstract static class HorizontalLayoutMixin {
        @JsonCreator
        public HorizontalLayoutMixin(
            @JsonProperty("gap") double gap,
            @JsonProperty("sideMargin") double sideMargin
        ) {}
    }

    public abstract static class VerticalLayoutMixin {
        @JsonCreator
        public VerticalLayoutMixin(
            @JsonProperty("gap") double gap,
            @JsonProperty("sideMargin") double sideMargin
        ) {}
    }

    public abstract static class ButtonMixin {
        @JsonCreator
        ButtonMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("img") List<String> img,
            @JsonProperty("textConfig") TextConfig textConfig,
            @JsonProperty("normalizedPosition") Vector2 normalizedPosition,
            @JsonProperty("clickActions") List<DataBinding> clickActions
        ) {}
    }

    public abstract static class LabelMixin {
        @JsonCreator
        LabelMixin(
            @JsonProperty("active") boolean active,
            @JsonProperty("name") String name,
            @JsonProperty("shape") Shape shape,
            @JsonProperty("shapeColor") Color shapeColor,
            @JsonProperty("img") List<String> img,
            @JsonProperty("textConfig") TextConfig textConfig,
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
        this.mapper.addMixIn(DynamicContainer.class, DynamicContainerMixin.class);
        this.mapper.addMixIn(ILayoutStrategy.class, LayoutMixin.class);
        this.mapper.addMixIn(HorizontalLayout.class, HorizontalLayoutMixin.class);
        this.mapper.addMixIn(VerticalLayout.class, VerticalLayoutMixin.class);
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
