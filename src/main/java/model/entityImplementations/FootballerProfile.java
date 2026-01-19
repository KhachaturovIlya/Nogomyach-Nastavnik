package model.entityImplementations;

import model.subclasses.BaseFootballerCharacteristics;
import model.subclasses.Nationality;
import model.subclasses.Role;
import model.entityInterfaces.IFootballerProfile;
import model.subclasses.FootballerCharacteristicsEnum;

import java.util.List;

public class FootballerProfile implements IFootballerProfile {
    private final String _name;
    private final Nationality _nationality;
    private String _club;
    private List<Role> _prefered_roles;
    private short _number;
    private short _age;
    private int _transfer_cost;
    private BaseFootballerCharacteristics _characteristics;

    public FootballerProfile(String name, Nationality nationality, String club, List<Role> prefered_roles, short number,
    BaseFootballerCharacteristics characteristics) {
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
    public List<Role> preferedRoles() {
        return _prefered_roles;
    }

    @Override
    public void addRole(Role role) {
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
    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return _characteristics.characteristic(characteristic);
    }

    @Override
    public void increaseCharacteristci(FootballerCharacteristicsEnum characteristic, short add) {
        _characteristics.increaseCharacteristic(characteristic, add);
    }

    @Override
    public void decreaseCharacteristci(FootballerCharacteristicsEnum characteristic, short loss) {
        _characteristics.decreaseCharacteristic(characteristic, loss);
    }

    @Override
    public String club() {
        return _club;
    }

    @Override
    public void setClub(String club) {
        _club = club;
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
    public Nationality nationality() {
        return _nationality;
    }
}