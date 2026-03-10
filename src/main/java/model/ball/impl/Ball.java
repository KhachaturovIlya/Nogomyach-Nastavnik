package model.ball.impl;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import model.ball.IBall;
import shared.Vector3;

public class Ball implements IBall {
	@Getter @Setter
    private Vector3 speed = new Vector3(0, 0, 0);
	@Getter @Setter
    private Vector3 acceleration = new Vector3(0, 0, 0);
	@Getter @Setter
    private Vector3 position;

    public Ball(Vector3 position) {
        this.position = position;
    }

    @Override
    public void move(Vector3 shift) {
        position.addLocal(shift);
    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {
        speed.addLocal(speedAdd);
    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {
        speedLoss.mulLocal(-1.0);
        speed.addLocal(speedLoss);
    }
}