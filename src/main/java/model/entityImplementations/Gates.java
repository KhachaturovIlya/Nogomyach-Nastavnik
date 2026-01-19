package model.entityImplementations;

import model.entityInterfaces.IGates;
import shared.Vector3;

public class Gates implements IGates {
    private static final double GATES_WIDTH = 7.32;
    private static final double GATES_HEIGHT = 2.5;
    private Vector3 _size = new Vector3(GATES_WIDTH, 0.0, GATES_HEIGHT);
    private Vector3 _position;

    public Gates(Vector3 position) {
        _position = position;
    }

    @Override
    public Vector3 position() {
        return _position;
    }

    @Override
    public Vector3 size() {
        return _size;
    }

    @Override
    public double width() {
        return _size.x;
    }

    @Override
    public double length() {
        return _size.y;
    }

    @Override
    public double height() {
        return _size.z;
    }
}
