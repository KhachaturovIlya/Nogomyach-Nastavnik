package shared;

public record VisualWidgetDTO(
    Shape shape,
    Color shapeColor,
    String text,
    Color textColor,
    TextType textType,
    Vector2 position,
    boolean button
) {}
