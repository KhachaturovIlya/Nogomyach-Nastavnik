package model.entityInterfaces;

import model.components.*;
import model.subclasses.FootballCharacteristics;
import model.subclasses.Roles;

import java.util.List;

public interface IFootballerProfile extends IHasName, IHasAge, IClubMember, IHasNationality {
    short number();
    void setNumber(short number);

    List<Roles> preferedRoles();
    void addRole(Roles role);

    int transferCost();
    void setTransferCost(int cost);
    void increaseTransferCost(int costAdd);
    void decreaseTransferCost(int costLoss);

    short characteristic(FootballCharacteristics characteristic);
    void increaseCharacteristci(FootballCharacteristics characteristic, short add);
    void decreaseCharacteristci(FootballCharacteristics characteristic, short loss);
}