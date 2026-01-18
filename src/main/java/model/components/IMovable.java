package model.components;

import shared.Vector3;

public interface IMovable extends IHasPosition {
    void move(Vector3 shift);

    // пока хз как мы будем считать скорость

    Vector3 speed();
    Vector3 acceleration();
    void setSpeed(Vector3 speed);
    void accelerate(Vector3 speedAdd);
    void decelerate(Vector3 speedLoss);
}