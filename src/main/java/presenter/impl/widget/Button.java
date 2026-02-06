package presenter.impl.widget;

import presenter.ICommand;
import shared.*;

import java.util.List;

public final class Button extends Widget {

    private List<DataBinding> clickActions;

    public Button(boolean active, String name, Shape shape, Color shapeColor, List<String> img, TextConfig textConfig,
                  Vector2 normalizedPosition, List<DataBinding> clickActions) {
        super(active, name, shape, shapeColor, img, textConfig, normalizedPosition);
        this.clickActions = clickActions;
    }

    public Button(Button button) {
        super(button);
        this.clickActions = button.clickActions;
    }

    @Override
    public Button wither(TextConfig textConfig) {
        Button button = new Button(this);
        button.textConfig = textConfig;
        return button;
    }

    @Override
    public Button clone() {
        return new Button(this);
    }

    public Button wither(List<DataBinding> clickActions) {
        Button button = new Button(this);
        button.clickActions = clickActions;

        return button;
    }

    public void setClickActions(List<DataBinding> clickActions) {
        this.clickActions = clickActions;
    }

    public List<DataBinding> getClickActions() {
        return clickActions;
    }
}
