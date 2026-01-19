package model.entityInterfaces;

import model.components.*;
import model.subclasses.FootballerCharacteristicsEnum;

public interface IPlayingFootballer extends IMovable, IHasSize {
    String role();
    void setRole(String role);
    double stamina();
    void increaseStamina(double add);
    void decreaseStamina(double loss);
    short ID();
    void setID(short ID);
    short charasteristic(FootballerCharacteristicsEnum characteristic);
}