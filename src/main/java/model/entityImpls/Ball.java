package model.entityImpls;

import model.entityInterfaces.IBall;
import shared.Vector3;

public class Ball implements IBall {
    private Vector3 _speed = new Vector3(0, 0, 0);
    private Vector3 _acceleration = new Vector3(0, 0, 0);
    private  Vector3 _position;

    public Ball(Vector3 position) {
        _position = position;
    }

    @Override
    public void move(Vector3 shift) {
        _position.addLocal(shift);
    }

    @Override
    public Vector3 speed() {
        return _speed;
    }

    @Override
    public void setSpeed(Vector3 speed) {
        _speed = speed;
    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {
        _speed.addLocal(speedAdd);
    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {
        speedLoss.mulLocal(-1.0);
        _speed.addLocal(speedLoss);
    }

    @Override
    public Vector3 acceleration() {
        return _acceleration;
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {
        _acceleration = acceleration;
    }

    @Override
    public Vector3 position() {
        return _position;
    }
}