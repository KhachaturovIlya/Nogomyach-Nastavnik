package presenter.impl.widget;

import shared.*;

import java.util.List;

public sealed abstract class Widget permits Button, Label, Container {
    static private int nextId = 0;

    final protected int id;
    protected String name;
    protected boolean active;

    final protected Shape shape;
    final protected Color shapeColor;

    final protected List<String> img;

    protected TextConfig textConfig;

    protected Vector2 normalizedPosition;

    public Widget(boolean active, String name, Shape shape, Color shapeColor, List<String> img,
                  TextConfig textConfig, Vector2 normalizedPosition) {
        this.id = nextId++;
        this.name = name;
        this.active = active;
        this.shape = shape;
        this.shapeColor = shapeColor;
        this.img = img;
        this.textConfig = textConfig;
        this.normalizedPosition = normalizedPosition;
    }

    public Widget(Widget widget) {
        this.id = nextId++;
        this.name = widget.name;
        this.active = widget.active;
        this.shape = widget.shape;
        this.shapeColor = widget.shapeColor;
        this.img = widget.img;
        this.textConfig = widget.textConfig;
        this.normalizedPosition = widget.normalizedPosition;
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

    public TextConfig getTextConfig() {
        return textConfig;
    }

    public Vector2 getNormalizedPosition() {
        return normalizedPosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNormalizedPosition(Vector2 normalizedPosition) {
        this.normalizedPosition = normalizedPosition;
    }

    public void setTextConfig(TextConfig textConfig) {
        this.textConfig = textConfig;
    }

    public void disable() {
        this.active = false;

    }
    public void enable() {
        this.active = true;
    }

    abstract public Widget wither(TextConfig textConfig);

    abstract public Widget clone();
}