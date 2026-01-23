package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Container extends Widget {
    Map<Integer, Widget> children;

    public Container(boolean active, String name, Shape shape, Color shapeColor,
                     String textId, Color textColor, Vector2 normalizedPosition, List<Widget> children) {
        super(active, name, shape, shapeColor, textId, textColor, normalizedPosition);

        Map<Integer, Widget> map = new HashMap<>();
        for (Widget widget : children) {
            map.put(widget.getId(), widget);
        }

        this.children = map;
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
