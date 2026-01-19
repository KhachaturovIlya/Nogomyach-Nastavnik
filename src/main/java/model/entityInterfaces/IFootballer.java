package model.entityInterfaces;

import model.components.*;
import model.subclasses.FootballCharacteristicsEnum;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public interface IFootballer extends IMovable, IHasName, IHasSize {
    // пусть будет просто как ни к чему не обязывающая подсказка игроку
    String role();
    void setRole(String role);
    List<Roles> preferedRoles();

    short age();

    short number();

    short charasteristic(FootballCharacteristicsEnum characteristic) throws InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}