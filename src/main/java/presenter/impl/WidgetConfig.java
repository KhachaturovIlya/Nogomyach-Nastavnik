package presenter.impl;

import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.List;
import java.util.Map;

public record WidgetConfig (
    boolean active,
    Shape shape,
    Color shapeColor,
    String text,
    Color textColor,
    Vector2 position,

    String actionName,

    List<WidgetConfig> subLabels,
    List<WidgetConfig> subButtons
) {
    public List<WidgetConfig> subLabels() { return subLabels != null ? subLabels : List.of(); }
    public List<WidgetConfig> subButtons() { return subButtons != null ? subButtons : List.of(); }
}
