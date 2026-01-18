package presenter.impl;

import shared.Color;
import shared.Shape;
import shared.Vector2;

public record WidgetConfig (
    int id,
    Shape shape,
    Color shapeColor,
    String text,
    Color textColor,
    Vector2 position,

    String actionName
) {}
