package model.servicesImpls;

import model.entityImpls.Squad;
import model.entityInterfaces.IFormation;
import model.entityInterfaces.ISquad;
import model.servicesInterfaces.ISquadFactory;
import model.subclasses.Role;

import java.util.ArrayList;
import java.util.Map;

public class SquadFactory implements ISquadFactory {

	@Override
	public ISquad create(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench) {
		return new Squad(formation, startingXI, bench);
	}
}