package presenter.impl;

import shared.Color;
import shared.Shape;
import shared.Vector2;

import java.util.*;

public class Widget {
    // Fields:

    final private int id;
    private boolean active;

    final private Shape shape;
    final private Color shapeColor;

    private String text;
    final private Color textColor;

    final private Vector2 normalizedPosition;

    private final Map<Integer, Widget> childrenLabels;
    private final Map<Integer, Widget> childrenButtons;

    private final List<Runnable> clickActions;
    // Methods:

    private Widget(int id, boolean active, Shape shape, Color shapeColor, String text,
        Color textColor, Vector2 normalizedPosition, Map<Integer, Widget> childrenLabels,
        Map<Integer, Widget> childrenButtons, List<Runnable> clickActions) {
        this.id = id;
        this.active = active;
        this.shape = shape;
        this.shapeColor = shapeColor;
        this.text = text;
        this.textColor = textColor;
        this.normalizedPosition = normalizedPosition;

        this.childrenLabels = Objects.requireNonNullElseGet(childrenLabels, HashMap::new);
        this.childrenButtons = Objects.requireNonNullElseGet(childrenButtons, HashMap::new);

        this.clickActions = Objects.requireNonNullElseGet(clickActions, ArrayList::new);
    }

    public void executeClick() {
        if (!active) return;

        for (Runnable action : clickActions) {
            action.run();
        }
    }

    public Widget(Widget other) {
        this.id = other.id;
        this.active = other.active;
        this.shape = other.shape;
        this.shapeColor = other.shapeColor;
        this.text = other.text;
        this.textColor = other.textColor;
        this.normalizedPosition = other.normalizedPosition;
        this.childrenButtons = other.childrenButtons;
        this.childrenLabels = other.childrenLabels;
        this.clickActions = other.clickActions;
    }

    public static Widget createButton(int id, boolean active, Shape shape, Color color, Vector2 position,
        List<Runnable> clickActions) {
        return new Widget(id, active, shape, color, "", color, position, null, null, clickActions);
    }

    public static Widget createLabel(int id, boolean active, Shape shape, Color shapeColor,
        String text, Color textColor, Vector2 position, Map<Integer, Widget> childrenLabels,
            Map<Integer, Widget> childrenButtons, List<Runnable> clickActions) {
        return new Widget(id, active, shape, shapeColor, text, textColor, position, childrenLabels, childrenButtons, null);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void sleep() {
        active = false;
    }

    public void awake() {
        active = true;
    }

    // Getters:

    public boolean isActive() {
        return active;
    }

    public boolean isSleeping() {
        return !active;
    }

    public int getId() {
        return id;
    }

    public Shape getShape() {
        return shape;
    }

    public Color getShapeColor() {
        return shapeColor;
    }

    public String getText() {
        return text;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Vector2 getNormalizedPosition() {
        return normalizedPosition;
    }
}
