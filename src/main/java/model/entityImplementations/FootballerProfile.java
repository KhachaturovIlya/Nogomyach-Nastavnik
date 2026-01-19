package model.entityImplementations;

import model.subclasses.Roles;
import model.entityInterfaces.IFootballerProfile;
import model.subclasses.FootballCharacteristics;

import java.util.List;

public class FootballerProfile implements IFootballerProfile {
    public static final int CHARACTERISTICS_CNT = 24;
    private final String _name;
    private final String _nationality;
    private String _club;
    private List<Roles> _prefered_roles;
    private short _number;
    private short _age;
    private int _transfer_cost;
    private short[] _characteristics;

    public FootballerProfile(String name, String nationality, String club, List<Roles> prefered_roles, short number) {
        _name = name;
        _nationality = nationality;
        _club = club;
        _prefered_roles = prefered_roles;
        _number = number;
        _characteristics = new short[CHARACTERISTICS_CNT];
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
    public short characteristic(FootballCharacteristics characteristic) {
        return _characteristics[characteristic.array_pos];
    }

    @Override
    public void increaseCharacteristci(FootballCharacteristics characteristic, short add) {
        _characteristics[characteristic.array_pos] += add;
    }

    @Override
    public void decreaseCharacteristci(FootballCharacteristics characteristic, short loss) {
        _characteristics[characteristic.array_pos] -= loss;
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
    public String nationality() {
        return _nationality;
    }
}