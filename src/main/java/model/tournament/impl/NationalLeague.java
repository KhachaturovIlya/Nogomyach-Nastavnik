package model.tournament.impl;

import lombok.Getter;
import lombok.Setter;
import model.tournament.regulations.impl.LeagueRegulations;
import model.tournament.INationalLeague;

import java.security.InvalidParameterException;
import java.util.*;

public class NationalLeague implements INationalLeague {
	@Getter
	private final String name;
	@Getter
	private ArrayList<TournamentTableNote> tournamentTable;
	@Getter
	private LeagueRegulations regulations;
	@Setter
	private Map<String, List<MatchNote>> schedule;
	@Getter
	private short currentTour;


	public NationalLeague(String name, LeagueRegulations regulations) throws InvalidParameterException {
		if (0 != regulations.getAmountOfTeams() % 2) {
			throw new InvalidParameterException("amount of teams must be even (league '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		tournamentTable = new ArrayList<>(this.regulations.getAmountOfTeams());
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		List<MatchNote> res = new ArrayList<>(regulations.getAmountOfTeams() / 2);
		schedule.forEach((_, value) -> res.add(value.get(currentTour)));
		return res;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		return schedule.get(team);
	}

	@Override
	public List<MatchNote> currentStageMatches() {
		List<MatchNote> res = new ArrayList<>(regulations.getAmountOfTeams() / 2);
		schedule.forEach((_, value) -> res.add(value.get(currentTour - 1)));
		return res;
	}

	@Override
	public void addTeam(String team) {
		if (tournamentTable.size() == regulations.getAmountOfTeams()) {
			throw new IndexOutOfBoundsException("league is already full");
		}

		tournamentTable.add(new TournamentTableNote(team));
	}

	@Override
	public void removeTeam(String team) {
		tournamentTable.removeIf(t -> t.getTeamName().equals(team));
	}

	@Override
	public void increaseTour() {
		currentTour += 1;
	}

	@Override
	public void resetResults() {
		tournamentTable.forEach(t -> t.reset());
		tournamentTable.sort(Comparator.comparing(TournamentTableNote::getTeamName));
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!schedule.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (league - '" + name + "', replaceTeam)");
		}
		int index = tournamentTable.indexOf(oldTeam);
		tournamentTable.set(index, new TournamentTableNote(newTeam));
		tournamentTable.sort(Comparator.comparing(TournamentTableNote::getTeamName));
	}

	@Override
	public List<String> getTeams() {
		List<String> res = new ArrayList<>(regulations.getAmountOfTeams());
		tournamentTable.forEach(note -> res.add(note.getTeamName()));
		return res;
	}
}