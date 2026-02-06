package presenter.impl.interfaces;

import presenter.impl.widget.Container;
import presenter.impl.widget.Widget;

import java.util.List;

public interface ILayoutStrategy {
    void apply(List<Widget> widgets);
}
