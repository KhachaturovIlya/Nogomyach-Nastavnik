package presenter.impl.widget;

import presenter.impl.interfaces.ILayoutStrategy;
import shared.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public non-sealed class Container extends Widget {
    protected Map<Integer, Widget> children;

    public Container(boolean active, String name, Shape shape, Color shapeColor, List<String> img,
                     TextConfig textConfig, Vector2 normalizedPosition, List<Widget> children) {
        super(active, name, shape, shapeColor, img, textConfig, normalizedPosition);

        Map<Integer, Widget> map = new HashMap<>();
        for (Widget widget : children) {
            map.put(widget.getId(), widget);
        }

        this.children = map;
    }

    public Container(Container container) {
        super(container);
        this.children = container.children;
    }

    @Override
    public Container clone() {
        return new Container(this);
    }

    public Map<Integer, Widget> getChildren() {
        return children;
    }

    public Widget getChild(int id) {
        return children.get(id);
    }

    public Widget findChild(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) throw new IllegalArgumentException("ids cannot be null or empty");

        if (ids.size() == 1 && ids.getFirst() == this.id) return this;

        List<Integer> newIds = ids.subList(1, ids.size());

        Widget current = this;
        for (Integer id : newIds) {
            if (current instanceof Container container) {
                current = container.getChild(id);
                if (current == null) {
                    throw new RuntimeException("Trying to access non-existing widget with id-path");
                }
            } else {
                throw new RuntimeException("Trying to access child in non-container class");
            }
        }
        return current;
    }

    @Override
    public Container wither(TextConfig textConfig) {
        Container container = new Container(this);
        container.textConfig = textConfig;

        return container;
    }

    public Widget findChild(String stringId) {
        if (stringId ==  null || stringId.isEmpty())
            throw new IllegalArgumentException("stringId cannot be null or empty");

        if (stringId.equals(this.getName())) {
            return this;
        }

        for (Widget widget : children.values()) {
            if (widget instanceof Container container) {
                Widget subFound = container.findChild(stringId);
                if (subFound != null) return subFound;
            } else if (widget.getName().equals(stringId)) {
                return widget;
            }
        }

        return null;
    }
}
