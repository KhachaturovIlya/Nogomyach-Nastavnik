package model.entityImpls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.subclasses.BaseFootballerCharacteristics;
import model.subclasses.Nationality;
import model.subclasses.Role;
import model.entityInterfaces.IFootballerProfile;
import model.subclasses.FootballerCharacteristicsEnum;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

public class FootballerProfile implements IFootballerProfile {
    private final String _name;
    private final Nationality _nationality;
    private List<Role> _preferedRoles;
    private short _number;
    private final LocalDate _dateOfBirth;
    private int _transferCost;

    private boolean _injured;
    private short _daysToHeal;

    private double _currentPhysicalForm = 1.0;
    private double _currentEmotionalState = 1.0;

    private BaseFootballerCharacteristics _characteristics;

	private boolean isTransferCostValid(int transferCost) {
		return  0 <= transferCost;
	}


	@JsonCreator
    public FootballerProfile(@JsonProperty("name")String name, @JsonProperty("nationality")Nationality nationality,
	@JsonProperty("prefered roles")List<Role> preferedRoles, @JsonProperty("date of birth")LocalDate dateOfBirth,
	@JsonProperty("number")short number, @JsonProperty("transfer cost")int transferCost,
	@JsonProperty("characteristics")BaseFootballerCharacteristics characteristics)
	throws InvalidParameterException {
		if (!isTransferCostValid(transferCost)) {
			throw new InvalidParameterException("invalid number: " + number);
		}
        _name = name;
        _nationality = nationality;
        _preferedRoles = preferedRoles;
		_dateOfBirth = dateOfBirth;
        _number = number;
		_transferCost = transferCost;
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
        return _preferedRoles;
    }

    @Override
    public void addRole(Role role) {
        _preferedRoles.add(role);
    }

    @Override
    public int transferCost() {
        return _transferCost;
    }

    @Override
    public void setTransferCost(int cost) {
        _transferCost = cost;
    }

    @Override
    public void increaseTransferCost(int costAdd) {
        _transferCost += costAdd;
    }

    @Override
    public void decreaseTransferCost(int costLoss) {
        _transferCost -= costLoss;
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
	public LocalDate dateOfBirth() {
		return _dateOfBirth;
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