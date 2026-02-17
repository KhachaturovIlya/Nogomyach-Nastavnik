package model.squad;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.enums.Role;
import model.formation.Formation;

import java.util.ArrayList;
import java.util.Map;
import java.util.NoSuchElementException;

@AllArgsConstructor
public class Squad implements ISquad {
	@Getter
	private Formation formation;
	@Getter
	private Map<Short, Role> startingXI;
	@Getter
	private ArrayList<Short> bench;


	@Override
	public void replacePlayerInStartingXI(short oldNumber, short newNumber) {
		Role role = startingXI.get(oldNumber);
		startingXI.put(newNumber, role);
		startingXI.remove(oldNumber);
	}

	@Override
	public void replacePlayerOnBench(short oldNumber, short newNumber) {
		var role = bench.stream().filter(n -> n == oldNumber).findAny();

		if (role.isEmpty()) {
			throw new NoSuchElementException("no player with " + oldNumber + " number");
		}

		bench.add(newNumber);
		bench.remove(oldNumber);
	}

	@Override
	public void changeFormation(Formation formation) {
		this.formation = formation;
		startingXI.clear();
		bench.clear();
	}
}