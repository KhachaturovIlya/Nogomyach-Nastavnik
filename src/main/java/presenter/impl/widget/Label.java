package presenter.impl.widget;

import shared.*;

import java.util.List;

public final class Label extends Widget {
    public Label(boolean active, String name, Shape shape, Color shapeColor,
                 TextConfig textConfig, Vector2 normalizedPosition) {
        super(active, name, shape, shapeColor, textConfig, normalizedPosition);
    }

    public Label(Label label) {
        super(label);
    }

    @Override
    public Widget wither(TextConfig textConfig) {
        Label label = new Label(this);

        label.textConfig = textConfig;
        return label;
    }
}