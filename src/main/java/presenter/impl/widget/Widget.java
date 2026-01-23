package presenter.impl.widget;

import shared.Color;
import shared.Shape;
import shared.Vector2;

public sealed abstract class Widget permits Button, Label, Container {
    static private int nextId = 0;

    final protected int id;
    protected String name;
    protected boolean active;

    final protected Shape shape;
    final protected Color shapeColor;

    final protected String textId;
    final protected Color textColor;

    final protected Vector2 normalizedPosition;

    public Widget(boolean active, String name, Shape shape, Color shapeColor,
                  String textId, Color textColor, Vector2 normalizedPosition) {
        this.id = nextId++;
        this.name = name;
        this.active = active;
        this.shape = shape;
        this.shapeColor = shapeColor;
        this.textId = textId;
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

    public String getName() {
        return name;
    }

    public String getTextId() {
        return textId;
    }

    public Color getTextColor() {
        return textColor;
    }

    public Vector2 getNormalizedPosition() {
        return normalizedPosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void disable() {
        this.active = false;

    }
    public void enable() {
        this.active = true;
    }
}
