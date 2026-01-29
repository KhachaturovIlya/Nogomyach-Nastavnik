package model.entityImpls;

import model.entityInterfaces.IFormation;
import model.entityInterfaces.ISquad;
import model.subclasses.Role;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

public class Squad implements ISquad {
	private IFormation _formation;
	private Map<Short, Role> _startingXI;
	private ArrayList<Short> _bench;



	public Squad(IFormation formation, Map<Short, Role> startingXI, ArrayList<Short> bench) {
		_formation = formation;
		_startingXI = startingXI;
		_bench = bench;
	}

	@Override
	public IFormation formation() {
		return _formation;
	}

	@Override
	public Map<Short, Role> startingXI() {
		return _startingXI;
	}

	@Override
	public ArrayList<Short> bench() {
		return _bench;
	}

	@Override
	public void replacePlayerInStartingXI(short oldNumber, short newNumber) {
		Role role = _startingXI.get(oldNumber);
		_startingXI.put(newNumber, role);
		_startingXI.remove(oldNumber);
	}

	@Override
	public void replacePlayerOnBench(short oldNumber, short newNumber) {
		var role = _bench.stream().filter(n -> n == oldNumber).findAny();

		if (role.isEmpty()) {
			throw new NoSuchElementException("no player with " + oldNumber + " number");
		}

		_bench.add(newNumber);
		_bench.remove(oldNumber);
	}

	@Override
	public void changeFormation(IFormation formation) {
		_formation = formation;
		_startingXI.clear();
		_bench.clear();
	}
}