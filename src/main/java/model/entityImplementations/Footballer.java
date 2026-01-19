package model.entityImplementations;

import model.subclasses.Roles;
import model.entityInterfaces.IFootballer;
import model.subclasses.FootballCharacteristics;
import shared.Vector3;

import java.util.List;

public class Footballer implements IFootballer {
    private FootballerProfile _profile;
    private Vector3 _speed = new Vector3(0, 0, 0);
    private Vector3 _acceleration = new Vector3(0, 0, 0);

    //пускай у нас выносливость измеряется в процентах
    private double _stamina = 1.0;

    private String _role;
    private Vector3 _size;
    private Vector3 _position;

    public Footballer(FootballerProfile profile, String role) {
        _profile = profile;
        _role = role;

        // пока хз что сделать с размером футболиста
        _size = new Vector3(0.25, 0.25, profile.characteristic(FootballCharacteristics.HEIGHT));
    }

    @Override
    public String role() {
        return _role;
    }

    @Override
    public void setRole(String role) {
        _role = role;
    }

    @Override
    public short number() {
        return _profile.number();
    }

    @Override
    public short charasteristic(FootballCharacteristics characteristic) {
        return _profile.characteristic(characteristic);
    }

    // для менюшки с информацией об игроке - как в фифе
    @Override
    public List<Roles> preferedRoles() {
        return _profile.preferedRoles();
    }

    public short age() {
        return _profile.age();
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

    @Override
    public String name() {
        return _profile.name();
    }
}