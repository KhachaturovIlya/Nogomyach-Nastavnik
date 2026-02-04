package model.repoImpls;

import model.repoInterfaces.ISwissSystemCup;
import model.subclasses.IRegulations;
import model.subclasses.MatchNote;
import model.subclasses.SwissSystemCupRegulations;
import model.subclasses.TournamentTableNote;
import shared.IsPowerOfTwo;

import java.security.InvalidParameterException;
import java.util.*;

public class SwissSystemCup implements ISwissSystemCup {
	private final String name;
	private ArrayList<TournamentTableNote> leagueTable;
	private short currentTour = 1;
	private List<MatchNote> indirectPlayOffPairs;
	private List<MatchNote> playOffPairs;
	private Map<String, List<MatchNote>> leaguePhaseOpponents;
	private SwissSystemCupRegulations regulations;


	public SwissSystemCup(String name, SwissSystemCupRegulations regulations, List<String> teams) {
		if (regulations.amountOfTeams() != teams.size() ||
				regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs() > regulations.amountOfTeams()) {
			throw new InvalidParameterException(
					"number of teams in regulations and in reality are different (cup '" + name + "')");
		}
		if (!IsPowerOfTwo.check(regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs() / 2)) {
			throw new InvalidParameterException("amount of teams in cup must be power of 2 (cup '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		leagueTable = new ArrayList<>(this.regulations.amountOfTeams());
		teams.forEach(t -> leagueTable.add(new TournamentTableNote(t)));
	}

	@Override
	public void resetResults() {
		leagueTable.clear();
		leaguePhaseOpponents.clear();
		indirectPlayOffPairs.clear();
		playOffPairs.clear();
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!leaguePhaseOpponents.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (cup - '" + name + "', replaceTeam)");
		}
		int index = leagueTable.indexOf(oldTeam);
		leagueTable.set(index, new TournamentTableNote(newTeam));
		leagueTable.sort(Comparator.comparing(TournamentTableNote::team));
	}

	@Override
	public List<String> teams() {
		if (currentTour < 9) {
			List<String> res = new ArrayList<>(regulations.amountOfTeams());
			leagueTable.forEach(note -> res.add(note.team()));
			return res;
		}
		if (9 <= currentTour && 11 > currentTour) {
			List<String> res = 
				new ArrayList<>(regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs());
			
			for (int i = 0; i < regulations.directPlayOffClubs() + regulations.indirectPlayOffClubs(); i += 1) {
				res.add(leagueTable.get(i).team());
			}
			return res;
		}
		List<String> res = new ArrayList<>(playOffPairs.size() * 2);
		playOffPairs.forEach(p -> {
			res.add(p.awayTeam());
			res.add(p.awayTeam());
		});
		return res;
	}

	@Override
	public IRegulations regulations() {
		return regulations;
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		if (currentTour < 9) {
			List<MatchNote> res = new ArrayList<>(regulations.amountOfTeams() / 2);
			leaguePhaseOpponents.forEach((key, value) -> res.add(value.get(currentTour - 1)));
			return res;
		}
		if (9 <= currentTour && 11 > currentTour) {
			return indirectPlayOffPairs;
		}
		return playOffPairs;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		if (currentTour < 9) {
			return leaguePhaseOpponents.get(team);
		}
		if (9 <= currentTour && 11 > currentTour) {
			return List.of(indirectPlayOffPairs.stream().filter(p -> p.containsTeam(team)).findAny().get());
		}
		return List.of(playOffPairs.stream().filter(p -> p.containsTeam(team)).findAny().get());
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public ArrayList<TournamentTableNote> leaguePhaseTable() {
		return leagueTable;
	}

	@Override
	public void setTeams(List<String> teams) {
		if (teams.size() != regulations.amountOfTeams()) {
			throw new InvalidParameterException("invalid number of teams (cup - '" + name + "', setTeams)");
		}
		var newTable = new ArrayList<TournamentTableNote>(regulations.amountOfTeams());
		teams.forEach(t -> newTable.add(new TournamentTableNote(t)));
		leagueTable = newTable;
	}

	@Override
	public void setLeaguePhaseOpponents(Map<String, List<MatchNote>> opponents) {
		leaguePhaseOpponents = opponents;
	}

	@Override
	public void setIndirectPlayOffPairs(List<MatchNote> pairs) {
		indirectPlayOffPairs = pairs;
	}

	@Override
	public void setPlayOffPairs(List<MatchNote> pairs) {
		playOffPairs = pairs;
	}

	@Override
	public short currentTour() {
		return currentTour;
	}

	@Override
	public void increaseTour() {
		currentTour += 1;
	}

	@Override
	public List<MatchNote> teamLeaguePhaseOpponents(String team) {
		return leaguePhaseOpponents.get(team);
	}

	@Override
	public Map<String, List<MatchNote>> allTeamsLeaguePhaseOpponents() {
		return leaguePhaseOpponents;
	}

	@Override
	public List<MatchNote> indirectPlayOffPairs() {
		return indirectPlayOffPairs;
	}

	@Override
	public List<MatchNote> playOffPairs() {
		return playOffPairs;
	}
}