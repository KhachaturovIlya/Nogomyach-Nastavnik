package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

public final class Label extends Widget {
    public Label(boolean active, String name, Shape shape, Color shapeColor,
                 String textId, Color textColor, Vector2 normalizedPosition) {
        super(active, name, shape, shapeColor, textId, textColor, normalizedPosition);
    }
}