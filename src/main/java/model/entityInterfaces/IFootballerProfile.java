package model.entityInterfaces;

import model.components.*;
import model.subclasses.FootballerCharacteristicsEnum;
import model.subclasses.Role;

import java.util.List;

public interface IFootballerProfile extends IHasName, IHasAge, IClubMember, IHasNationality {
    short number();
    void setNumber(short number);

    List<Role> preferedRoles();
    void addRole(Role role);

    int transferCost();
    void setTransferCost(int cost);
    void increaseTransferCost(int costAdd);
    void decreaseTransferCost(int costLoss);

    short characteristic(FootballerCharacteristicsEnum characteristic);
    void increaseCharacteristci(FootballerCharacteristicsEnum characteristic, short add);
    void decreaseCharacteristci(FootballerCharacteristicsEnum characteristic, short loss);
}