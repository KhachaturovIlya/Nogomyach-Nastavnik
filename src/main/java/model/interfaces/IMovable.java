package model.interfaces;

import shared.Vector3;

public interface IMovable extends IHasPosition {
    void move(Vector3 shift);

    // пока хз как мы будем считать скорость

    Vector3 getSpeed();
    void setSpeed(Vector3 speed);
    void increaseSpeed(Vector3 speedAdd);
    void decreaseSpeed(Vector3 speedLoss);

    Vector3 getAcceleration();
    void setAcceleration(Vector3 acceleration);
}