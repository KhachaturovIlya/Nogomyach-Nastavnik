package model.footballer.impl;

import lombok.Getter;
import lombok.Setter;
import model.enums.Nationality;
import model.enums.Role;
import model.enums.FootballerCharacteristicsEnum;
import model.footballer.BaseFootballerCharacteristics;
import model.footballer.IFootballerProfile;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.util.List;

public class FootballerProfile implements IFootballerProfile {
	@Getter
    private final String name;
	@Getter
    private final Nationality nationality;
	@Getter
    private List<Role> preferedRoles;
	@Getter @Setter
    private short number;
	@Getter
    private final LocalDate dateOfBirth;
	@Getter @Setter
    private int transferCost;

	@Getter
    private boolean injured;
	@Getter
    private short daysToHeal;

	@Getter @Setter
    private double currentPhysicalForm = 1.0;
	@Getter @Setter
    private double currentEmotionalState = 1.0;

    private BaseFootballerCharacteristics characteristics;

	private boolean isTransferCostValid(int transferCost) {
		return  0 <= transferCost;
	}


    public FootballerProfile(String name, Nationality nationality, List<Role> preferedRoles, LocalDate dateOfBirth,
	short number, int transferCost,	BaseFootballerCharacteristics characteristics) throws InvalidParameterException {
		if (!isTransferCostValid(transferCost)) {
			throw new InvalidParameterException("invalid number: " + number);
		}
        this.name = name;
        this.nationality = nationality;
        this.preferedRoles = preferedRoles;
		this.dateOfBirth = dateOfBirth;
        this.number = number;
		this.transferCost = transferCost;
        this.characteristics = characteristics;
    }

    @Override
    public void addRole(Role role) {
        preferedRoles.add(role);
    }

    @Override
    public void increaseTransferCost(int costAdd) {
        transferCost += costAdd;
    }

    @Override
    public void decreaseTransferCost(int costLoss) {
        transferCost -= costLoss;
    }

    @Override
    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return characteristics.characteristic(characteristic);
    }

    @Override
    public BaseFootballerCharacteristics allCharacteristics() {
        return characteristics;
    }

    @Override
    public void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add) {
        characteristics.increaseCharacteristic(characteristic, add);
    }

    @Override
    public void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss) {
        characteristics.decreaseCharacteristic(characteristic, loss);
    }

    @Override
    public void setInjury(short daysToHeal) {
        injured = true;
        this.daysToHeal = daysToHeal;
    }

    @Override
    public void updateInjury() {
        daysToHeal -= 1;
    }

    @Override
    public void increasePhysicalForm(double add) {
        currentPhysicalForm += Math.min(1.0 - currentPhysicalForm, add);
    }

    @Override
    public void decreasePhysicalForm(double loss) {
        currentPhysicalForm -= Math.min(currentPhysicalForm, loss);
    }

    @Override
    public void increaseEmotionalState(double add) {
        currentEmotionalState += Math.min(1.0 - currentEmotionalState, add);
    }

    @Override
    public void decreaseEmotionalState(double loss) {
        currentEmotionalState -= Math.min(currentEmotionalState, loss);
    }
}