package model.field.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import model.ball.impl.Ball;
import model.ball.IBall;
import model.field.IField;
import model.footballer.IPlayingFootballer;
import shared.Vector3;

@NoArgsConstructor
public class Field implements IField {
    private static final double FIELD_LENGTH = 105.0;
    private static final double FIELD_WIDTH = 68.0;
    private static final short PLAYERS_CNT = 22;
	@Getter
    private final Vector3 size = new Vector3(FIELD_LENGTH, FIELD_WIDTH, 0.0);

    private IPlayingFootballer[] players = new IPlayingFootballer[PLAYERS_CNT];
    private short currentID;

	@Getter
    private IBall ball = new Ball(new Vector3(FIELD_LENGTH / 2.0, FIELD_WIDTH / 2.0, 0.0));


    @Override
    public short addPlayer(IPlayingFootballer player) {
        player.setID(currentID);
        players[currentID] = player;
        short tmp = currentID;
        currentID += 1;
        return tmp;
    }

    @Override
    public void substitutePlayer(short ID, IPlayingFootballer playerFromBench) {
        playerFromBench.setID(ID);
        players[ID] = playerFromBench;
    }

    @Override
    public double width() {
        return size.x;
    }

    @Override
    public double length() {
        return size.y;
    }

    @Override
    public double height() {
        return size.z;
    }
}