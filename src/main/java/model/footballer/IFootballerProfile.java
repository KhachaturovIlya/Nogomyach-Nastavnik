package model.footballer;

import model.interfaces.*;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;

import java.util.List;

public interface IFootballerProfile extends IHasName, IHasDateOfBirth, IHasNationality {
    short getNumber();
    void setNumber(short number);

    List<Role> getPreferedRoles();
    void addRole(Role role);

    int getTransferCost();
    void setTransferCost(int cost);
    void increaseTransferCost(int costAdd);
    void decreaseTransferCost(int costLoss);

    short characteristic(FootballerCharacteristicsEnum characteristic);
    BaseFootballerCharacteristics allCharacteristics();
    void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add);
    void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss);

    boolean isInjured();
    void setInjury(short daysToHeal);
    short getDaysToHeal();
    void updateInjury();

    double getCurrentPhysicalForm();
    void increasePhysicalForm(double add);
    void decreasePhysicalForm(double loss);
    void setCurrentPhysicalForm(double physicalForm);

    double getCurrentEmotionalState();
    void increaseEmotionalState(double add);
    void decreaseEmotionalState(double loss);
    void setCurrentEmotionalState(double emotionalState);
}