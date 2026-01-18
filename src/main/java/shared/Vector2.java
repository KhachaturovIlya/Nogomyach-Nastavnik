package shared;

public class Vector2 {
    public double x = 0;
    public double y = 0;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2(Vector2 p) {
        this.x = p.x;
        this.y = p.y;
    }

    public Vector2 add(Vector2 p) {
        return new Vector2(x + p.x, y + p.y);
    }

    public void addLocal(Vector2 p) {
        this.x += p.x;
        this.y += p.y;
    }

    public Vector2 sub(Vector2 p) {
        return new Vector2(x - p.x, y - p.y);
    }

    public void subLocal(Vector2 p) {
        this.x -= p.x;
        this.y -= p.y;
    }

    public Vector2 mul(double factor) {
        return new Vector2(x * factor, y * factor);
    }

    public void mulLocal(double factor) {
        this.x *= factor;
        this.y *= factor;
    }

    public Vector2 div(double factor) {
        return new Vector2(x / factor, y / factor);
    }

    public void divLocal(double factor) {
        this.x /= factor;
        this.y /= factor;
    }

    public Vector2 normalize() {
        double len = length();
        if (len != 0) {
            return new Vector2(x / len, y / len);
        }
        return new Vector2(0, 0);
    }

    public void normalizeLocal() {
        double len = length();
        if (len != 0) {
            x /= len;
            y /= len;
        }
    }

    public double dot(Vector2 p) {
        return x * p.x + y * p.y;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }
}
