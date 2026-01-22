package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

public final class Label extends Widget {
    public Label(boolean active, Shape shape, Color shapeColor, String text, Color textColor, Vector2 normalizedPosition) {
        super(active, shape, shapeColor, text, textColor, normalizedPosition);
    }
}