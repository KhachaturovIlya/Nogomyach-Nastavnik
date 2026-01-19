package model.entityImplementations;

import model.components.Roles;
import model.entityInterfaces.IFootballer;
import model.subclasses.FootballCharacteristicsEnum;
import shared.Vector3;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class Footballer implements IFootballer {
    private FootballerProfile _profile;
    private short _speed;
    private short _acceleration;
    private short _stamina;
    private String _role;
    private Vector3 _size;

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
    public List<Roles> preferedRoles() {
        return _profile.preferedRoles();
    }

    @Override
    public short charasteristic(FootballCharacteristicsEnum characteristic)
    throws InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return _profile.charasteristic(characteristic);
    }

    public short age() {
        return _profile.age();
    }

    @Override
    public void setSize(Vector3 size) {

    }

    @Override
    public Vector3 size() {
        return null;
    }

    @Override
    public double width() {
        return 0;
    }

    @Override
    public double length() {
        return 0;
    }

    @Override
    public double height() {
        return 0;
    }

    @Override
    public void move(Vector3 shift) {

    }

    @Override
    public Vector3 speed() {
        return null;
    }

    @Override
    public void setSpeed(Vector3 speed) {

    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {

    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {

    }

    @Override
    public Vector3 acceleration() {
        return null;
    }

    @Override
    public void setAcceleration(Vector3 acceleration) {

    }

    @Override
    public Vector3 position() {
        return null;
    }

    @Override
    public String name() {
        return _profile.name();
    }
}