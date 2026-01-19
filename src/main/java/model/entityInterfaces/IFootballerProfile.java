package model.entityInterfaces;

import model.components.*;
import model.subclasses.FootballCharacteristicsEnum;

import java.lang.reflect.InvocationTargetException;
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

    short charasteristic(FootballCharacteristicsEnum characteristic) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    void increaseCharacteristic(FootballCharacteristicsEnum characteristic, short add) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
    void decreaseCharacteristic(FootballCharacteristicsEnum characteristic, short loss) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException;
}