package shared;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

public sealed interface Shape {
    public double getWidth();
    public double getHeight();
    public boolean contains(double x, double y);
    public default boolean contains(Vector2 point) {
        return contains(point.x, point.y);
    }

    Shape compressedCopy(double x, double y);

    record Circle(double radius) implements Shape {
        @Override public double getWidth() { return radius; }
        @Override public double getHeight() { return radius; }
        @Override public Shape compressedCopy(double x, double y) {
            if (x <= 0 || y <= 0) {
                return new Circle(0);
            }
            if ( x >= y) {
                return new Shape.Circle(radius * y);
            }
            return new Shape.Circle(radius * x);
        }
        @Override public boolean contains(double x, double y) {
            return x >= -radius && x <= radius && y >= -radius && y <= radius;
        }
    }
    record Rectangle(double width, double height) implements Shape {
        @Override public double getWidth() { return width; }
        @Override public double getHeight() { return height; }
        @Override public Shape compressedCopy(double x, double y) {
            if (x <= 0 || y <= 0) {
                return new Rectangle(0, 0);
            }
            return new Rectangle(width * x, height * y);
        }
        @Override public boolean contains(double x, double y) {
            return x >= 0 && y >= 0 && x <= width && y <= height;
        }
    }
    record Square(double side) implements Shape {
        @Override public double getWidth() { return side; }
        @Override public double getHeight() { return side; }
        @Override public Shape compressedCopy(double x, double y) {
            if (x <= 0 || y <= 0) {
                return new Square(0);
            }
            if (x >= y) {
                return new Shape.Square(side * y);
            }
            return new Shape.Square(side * x);
        }
        @Override public boolean contains(double x, double y) {
            return x >= 0 && y >= 0 && x <= side && y <= side;
        }
    }
}