package model.footballer;

import model.interfaces.*;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;

public interface IPlayingFootballer extends IMovable, IHasSize {
    Role getRole();
    void setRole(Role role);
    double getStamina();
    void increaseStamina(double add);
    void decreaseStamina(double loss);
    short getID();
    void setID(short ID);
    short charasteristic(FootballerCharacteristicsEnum characteristic);
}