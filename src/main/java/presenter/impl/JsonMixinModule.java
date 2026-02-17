package presenter.impl;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.NoArgsConstructor;
import presenter.impl.interfaces.ILayoutStrategy;
import presenter.impl.widget.*;
import presenter.impl.widget.dinamicContainerPolicies.HorizontalLayout;
import presenter.impl.widget.dinamicContainerPolicies.VerticalLayout;
import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.List;

public class JsonMixinModule extends SimpleModule {
    public JsonMixinModule() {
        super("WidgetModule");

        setMixInAnnotation(Shape.class, ShapeMixin.class);
        setMixInAnnotation(Widget.class, WidgetMixin.class);
        setMixInAnnotation(Container.class, ContainerMixin.class);
        setMixInAnnotation(DynamicContainer.class, DynamicContainerMixin.class);
        setMixInAnnotation(ILayoutStrategy.class, LayoutMixin.class);
        setMixInAnnotation(HorizontalLayout.class, HorizontalLayoutMixin.class);
        setMixInAnnotation(VerticalLayout.class, VerticalLayoutMixin.class);
        setMixInAnnotation(Button.class, ButtonMixin.class);
        setMixInAnnotation(Label.class, LabelMixin.class);
        setMixInAnnotation(Vector2.class, Vector2Mixin.class);
    }

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

    public abstract static class Vector2Mixin {
        @JsonCreator
        Vector2Mixin(
                @JsonProperty("x") double x,
                @JsonProperty("y") double y
        ) {}
    }
}
