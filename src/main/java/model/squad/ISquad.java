package model.squad;

import model.enums.Role;
import model.formation.Formation;

import java.util.ArrayList;
import java.util.Map;

public interface ISquad {
	Formation getFormation();
	Map<Short, Role> getStartingXI();
	ArrayList<Short> getBench();
	void replacePlayerInStartingXI(short oldNumber, short newNumber);
	void replacePlayerOnBench(short oldNumber, short newNumber);
	void changeFormation(Formation formation);
}