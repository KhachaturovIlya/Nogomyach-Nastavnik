package shared;

public record VisualWidgetDTO(
    Shape shape,
    Color shapeColor,
    String text,
    Color textColor,
    Vector2 position
) {}
