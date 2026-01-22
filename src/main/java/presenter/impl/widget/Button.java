package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.List;

public final class Button extends Widget {
    private final List<String> clickActions;

    public Button(boolean active, Shape shape, Color shapeColor, String text, Color textColor,
           Vector2 normalizedPosition, List<String> clickActions) {
        super(active, shape, shapeColor, text, textColor, normalizedPosition);
        this.clickActions = clickActions;
    }

    public List<String> getClickActions() {
        return clickActions;
    }
}
