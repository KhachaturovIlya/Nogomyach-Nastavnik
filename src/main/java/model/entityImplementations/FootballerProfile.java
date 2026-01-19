package model.entityImplementations;

import model.components.Roles;
import model.entityInterfaces.IFootballerProfile;
import model.subclasses.FootballCharacteristicsEnum;
import model.subclasses.FootballerCharacteristics;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

public class FootballerProfile implements IFootballerProfile {
    private final String _name;
    private final String _nationality;
    private String _club;
    private List<Roles> _prefered_roles;
    private short _number;
    private short _age;
    private int _transfer_cost;
    private FootballerCharacteristics _characteristics;


    public FootballerProfile(String name, String nationality, String club, List<Roles> prefered_roles, short number,
    FootballerCharacteristics characteristics) {
        _name = name;
        _nationality = nationality;
        _club = club;
        _prefered_roles = prefered_roles;
        _number = number;
        _characteristics = characteristics;
    }


    @Override
    public short number() {
        return _number;
    }

    @Override
    public void setNumber(short number) {
        _number = number;
    }

    @Override
    public List<Roles> preferedRoles() {
        return _prefered_roles;
    }

    @Override
    public void addRole(Roles role) {
        _prefered_roles.add(role);
    }

    @Override
    public int transferCost() {
        return _transfer_cost;
    }

    @Override
    public void setTransferCost(int cost) {
        _transfer_cost = cost;
    }

    @Override
    public void increaseTransferCost(int costAdd) {
        _transfer_cost += costAdd;
    }

    @Override
    public void decreaseTransferCost(int costLoss) {
        _transfer_cost -= costLoss;
    }

    @Override
    public short charasteristic(FootballCharacteristicsEnum characteristic) throws NoSuchMethodException,
    InvocationTargetException, IllegalAccessException {
        Method getter = FootballerCharacteristics.class.getMethod(characteristic.name());
        return (short) getter.invoke(_characteristics);
    }

    @Override
    public void increaseCharacteristic(FootballCharacteristicsEnum characteristic, short add)
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String increaserName = "increase" + characteristic.name();
        Method increaser = FootballerCharacteristics.class.getMethod(increaserName);
        increaser.invoke(_characteristics, add);
    }

    @Override
    public void decreaseCharacteristic(FootballCharacteristicsEnum characteristic, short loss)
    throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String decreaserName = "decrease" + characteristic.name();
        Method decreaser = FootballerCharacteristics.class.getMethod(decreaserName);
        decreaser.invoke(_characteristics, loss);
    }

    @Override
    public short age() {
        return _age;
    }

    @Override
    public void increaseAge() {
        _age += 1;
    }

    @Override
    public String name() {
        return _name;
    }

    @Override
    public String nationality() {
        return _nationality;
    }

    @Override
    public String club() {
        return _club;
    }

    @Override
    public void setClub(String club) {
        _club = club;
    }
}