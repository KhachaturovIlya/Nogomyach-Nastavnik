package model.servicesInterfaces;

import model.entityInterfaces.IFormation;
import model.subclasses.Role;

public interface IMatchStartService {
    void addPlayerToStartingXI(short number, Role role);
    void addPlayerOnBench(short number);
    void replacePlayerInStartingXI(short numberOld, short numberNew);
    void replacePlayerOnBench(short numberOld, short numberNew);
    void swapPlayersInStartingXI(short numberX, short numberY);
    void setFormation(IFormation formation);
    boolean isFormationSet();
}