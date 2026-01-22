package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

public sealed abstract class Widget permits Button, Label, Container {
    static private int nextId = 0;

    final protected int id;
    protected boolean active;

    final protected Shape shape;
    final protected Color shapeColor;

    protected String text;
    final protected Color textColor;

    final protected Vector2 normalizedPosition;

    public Widget(boolean active, Shape shape, Color shapeColor, String text, Color textColor, Vector2 normalizedPosition) {
        this.id = nextId++;
        this.active = active;
        this.shape = shape;
        this.shapeColor = shapeColor;
        this.text = text;
        this.textColor = textColor;
        this.normalizedPosition = normalizedPosition;
    }

    public int getId() {
        return id;
    }

    public boolean isActive() {
        return active;
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

    public void setText(String text) {
        this.text = text;
    }

    public void disable() {
        this.active = false;

    }
    public void enable() {
        this.active = true;
    }
}
