package model.entityImpls;

import model.entityInterfaces.IBall;
import model.entityInterfaces.IField;
import model.entityInterfaces.IPlayingFootballer;
import shared.Vector3;

public class Field implements IField {
    private static final double FIELD_LENGTH = 105.0;
    private static final double FIELD_WIDTH = 68.0;
    private static final short PLAYERS_CNT = 22;
    private final Vector3 _size = new Vector3(FIELD_LENGTH, FIELD_WIDTH, 0.0);

    private IPlayingFootballer[] _players;
    private short _currentID;

    private IBall _ball;

    public Field() {
        _ball = new Ball(new Vector3(FIELD_LENGTH / 2.0, FIELD_WIDTH / 2.0, 0.0));
        _players = new IPlayingFootballer[PLAYERS_CNT];
    }

    @Override
    public short addPlayer(IPlayingFootballer player) {
        player.setID(_currentID);
        _players[_currentID] = player;
        short tmp = _currentID;
        _currentID += 1;
        return tmp;
    }

    @Override
    public void substitutePlayer(short ID, IPlayingFootballer playerFromBench) {
        playerFromBench.setID(ID);
        _players[ID] = playerFromBench;
    }

    @Override
    public IBall Ball() {
        return _ball;
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