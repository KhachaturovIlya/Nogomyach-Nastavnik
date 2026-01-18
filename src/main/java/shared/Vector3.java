package shared;

public class Vector3 {
    public double x = 0;
    public double y = 0;
    public double z = 0;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3(Vector3 other) {
        x = other.x;
        y = other.y;
        z = other.z;
    }

    public Vector3 add(Vector3 other) {
        return new Vector3(x + other.x, y + other.y, z + other.z);
    }

    public void addLocal(Vector3 other) {
        x += other.x;
        y += other.y;
        z = other.z;
    }

    public Vector3 sub(Vector3 other) {
        return new Vector3(x - other.x, y - other.y, x - other.z);
    }

    public void subLocal(Vector3 other) {
        x -= other.x;
        y -= other.y;
        z -= other.z;
    }

    public Vector3 mul(double factor) {
        return new Vector3(x * factor, y * factor, z * factor);
    }

    public void mulLocal(double factor) {
        x *= factor;
        y *= factor;
        z *= factor;
    }

    public Vector3 div(double factor) {
        return new Vector3(x / factor, y / factor, z/factor);
    }

    public void divLocal(double factor) {
        x /= factor;
        y /= factor;
        z /= factor;
    }

    public Vector3 normalize() {
        double len = length();
        if (len != 0) {
            return new Vector3(x / len, y / len, y/len);
        }
        return new Vector3(0, 0, 0);
    }

    public void normalizeLocal() {
        double len = length();
        if (len != 0) {
            x /= len;
            y /= len;
            z /= len;
        }
    }

    public double dot(Vector3 other) {
        return x * other.x + y * other.y + z * other.z;
    }

    public double length() {
        return Math.sqrt(x * x + y * y + z * z);
    }
}
