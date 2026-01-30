package presenter.impl.widget.dinamicContainerPolicies;

import presenter.impl.interfaces.ILayoutStrategy;
import presenter.impl.widget.Widget;
import shared.Vector2;

import java.util.List;

public class HorizontalLayout implements ILayoutStrategy {
    private final double gap;
    private final double sideMargin;

    public HorizontalLayout(double gap, double sideMargin) {
        this.gap = gap;
        this.sideMargin = sideMargin;
    }

    @Override
    public void apply(List<Widget> widgets) {
        double sumWidth = 0;

        for (Widget widget : widgets) {
            if (sumWidth + widget.getShape().getWidth() <= 1) {
                widget.setNormalizedPosition(new Vector2(sideMargin + sumWidth, sideMargin));
                widget.enable();

                sumWidth += widget.getShape().getWidth();
                sumWidth += gap;
            } else {
                widget.disable();
            }
        }
    }
}