package model.repoImpls;

import model.entityInterfaces.ITeam;
import model.repoInterfaces.ILeague;
import model.subclasses.TournamentTableNote;

import java.util.ArrayList;
import java.util.Comparator;

public class NationalLeague implements ILeague {
	private String _name;
	private ArrayList<TournamentTableNote> _table;
	private short _teamNumber;
	private short _filledTeamNumber;


	public NationalLeague(String name, short teamNumber) {
		_name = name;
		_teamNumber = teamNumber;
		_table = new ArrayList<>(_teamNumber);
	}

	@Override
	public ArrayList<TournamentTableNote> tournamentTable() {
		return _table;
	}

	@Override
	public void addTeam(ITeam team) {
		if (_filledTeamNumber > _teamNumber) {
			throw new IndexOutOfBoundsException("league is already full");
		}

		_table.set(_filledTeamNumber, new TournamentTableNote(team));
		_filledTeamNumber += 1;
	}

	@Override
	public void removeTeam(String teamName) {
		_table.removeIf(t -> t.team().name() == teamName);
		_filledTeamNumber -= 1;
	}

	@Override
	public void resetResults() {
		_table.forEach(t -> t.reset());
		_table.sort(Comparator.comparing(TournamentTableNote::teamName));
	}

	@Override
	public String name() {
		return _name;
	}
}