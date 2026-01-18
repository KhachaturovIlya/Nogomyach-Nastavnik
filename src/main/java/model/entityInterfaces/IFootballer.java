package model.entityInterfaces;

import model.components.IHasAge;
import model.components.IHasSize;
import model.components.IMovable;
import model.components.INameable;

public interface IFootballer extends IMovable, INameable, IHasAge, IHasSize {

    // пусть будет просто как ни к чему не обязывающая подсказка игроку
    String role();
    void setRole(String role);

    int number();
    void setNumber();

    int transferCost();
    void setTransferCost(int cost);

    int charasteristic(String characteristicName);
    void upgradeCharacteristic(String characteristicName, int difference);
}