package model.entityImpls;

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

    private boolean _injured = false;
    private short _daysToHeal;


    private double _currentPhysicalForm = 1.0;
    private double _currentEmotionalState = 1.0;

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
    public BaseFootballerCharacteristics allCharacteristics() {
        return _characteristics;
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
    public boolean injured() {
        return _injured;
    }

    @Override
    public void setInjury(short daysToHeal) {
        _injured = true;
        _daysToHeal = daysToHeal;
    }

    @Override
    public short daysToHeal() {
        return _daysToHeal;
    }

    @Override
    public void updateInjury() {
        _daysToHeal -= 1;
    }

    @Override
    public double currentPhysicalForm() {
        return _currentPhysicalForm;
    }

    @Override
    public void increasePhysicalForm(double add) {
        _currentPhysicalForm += Math.min(1.0 - _currentPhysicalForm, add);
    }

    @Override
    public void decreasePhysicalForm(double loss) {
        _currentPhysicalForm -= Math.min(_currentPhysicalForm, loss);
    }

    @Override
    public void setPhysicalForm(double physicalForm) {
        _currentPhysicalForm = physicalForm;
    }

    @Override
    public double currentEmotionalState() {
        return _currentEmotionalState;
    }

    @Override
    public void increaseEmotionalState(double add) {
        _currentEmotionalState += Math.min(1.0 - _currentEmotionalState, add);
    }

    @Override
    public void decreaseEmotionalState(double loss) {
        _currentEmotionalState -= Math.min(_currentEmotionalState, loss);
    }

    @Override
    public void setEmotionalState(double emotionalState) {
        _currentEmotionalState = emotionalState;
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