package model.repoImpls;

import model.entityInterfaces.ITeam;
import model.repoInterfaces.ILeague;
import model.subclasses.LeagueRegulations;
import model.subclasses.TournamentTableNote;

import java.util.ArrayList;
import java.util.Comparator;

public class NationalLeague implements ILeague {
	private String _name;
	private ArrayList<TournamentTableNote> _table;
	private LeagueRegulations _regulations;


	public NationalLeague(String name, LeagueRegulations regulations) {
		_name = name;
		_regulations = regulations;
		_table = new ArrayList<>(_regulations.amountOfTeams());
	}

	@Override
	public ArrayList<TournamentTableNote> tournamentTable() {
		return _table;
	}

	@Override
	public LeagueRegulations regulations() {
		return null;
	}

	@Override
	public void addTeam(ITeam team) {
		if (_table.size() == _regulations.amountOfTeams()) {
			throw new IndexOutOfBoundsException("league is already full");
		}

		_table.add(new TournamentTableNote(team));
	}

	@Override
	public void removeTeam(String teamName) {
		_table.removeIf(t -> t.team().name().equals(teamName));
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