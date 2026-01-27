package model.servicesInterfaces;

import model.entityInterfaces.IFormation;
import model.entityInterfaces.ISquad;
import model.subclasses.Role;

import java.util.ArrayList;
import java.util.Map;

public interface ISquadFactory {
	ISquad create(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench);
}