package model.tournament.impl;

import lombok.Getter;
import lombok.Setter;
import model.tournament.regulations.impl.SwissSystemCupRegulations;
import model.tournament.ISwissSystemCup;
import shared.IsPowerOfTwo;
import shared.Pair;

import java.security.InvalidParameterException;
import java.util.*;

public class SwissSystemCup implements ISwissSystemCup {
	@Getter
	private final String name;
	@Getter
	private List<TournamentTableNote> leaguePhaseTable;
	@Getter
	private short currentTour;
	@Getter @Setter
	private List<Pair<MatchNote>> indirectPlayOffPairs;
	private List<List<Pair<MatchNote>>> playOffPairs;
	@Setter
	private Map<String, List<MatchNote>> leaguePhaseOpponents;
	@Getter
	private SwissSystemCupRegulations regulations;


	public SwissSystemCup(String name, SwissSystemCupRegulations regulations, List<String> teams) {
		if (regulations.getAmountOfTeams() != teams.size() ||
				regulations.getDirectPlayOffClubs() + regulations.getIndirectPlayOffClubs() > regulations.getAmountOfTeams()) {
			throw new InvalidParameterException(
					"number of teams in regulations and in reality are different (cup '" + name + "')");
		}
		if (!IsPowerOfTwo.check(regulations.getDirectPlayOffClubs() + regulations.getIndirectPlayOffClubs() / 2)) {
			throw new InvalidParameterException("amount of teams in cup must be power of 2 (cup '" + name + "')");
		}
		if (getRegulations().getAmountOfTeams() % regulations.getPots() != 0) {
			throw new InvalidParameterException("amount of teams in cup must be multiple of amount of pots (cup '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		leaguePhaseTable = new ArrayList<>(this.regulations.getAmountOfTeams());
		playOffPairs = new ArrayList<>((int) (Math.log(regulations.getDirectPlayOffClubs()) / Math.log(2)));
		teams.forEach(t -> leaguePhaseTable.add(new TournamentTableNote(t)));
	}

	@Override
	public void resetResults() {
		leaguePhaseTable.clear();
		leaguePhaseOpponents.clear();
		indirectPlayOffPairs.clear();
		playOffPairs.clear();
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!leaguePhaseOpponents.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (cup - '" + name + "', replaceTeam)");
		}
		int index = leaguePhaseTable.indexOf(oldTeam);
		leaguePhaseTable.set(index, new TournamentTableNote(newTeam));
		leaguePhaseTable.sort(Comparator.comparing(TournamentTableNote::getTeamName));
	}

	@Override
	public List<String> getTeams() {
		List<String> res = new ArrayList<>(regulations.getAmountOfTeams());
		leaguePhaseTable.forEach(note -> res.add(note.getTeamName()));
		return res;
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		if (currentTour < regulations.getLeaguePhaseMatches()) {
			List<MatchNote> res = new ArrayList<>(regulations.getAmountOfTeams() / 2);
			leaguePhaseOpponents.forEach((_, value) -> res.add(value.get(currentTour)));
			return res;
		}
		if (regulations.getLeaguePhaseMatches() <= currentTour && regulations.getLeaguePhaseMatches() + 2 > currentTour) {
			List<MatchNote> res = new ArrayList<>(regulations.getIndirectPlayOffClubs() / 2);
			indirectPlayOffPairs.forEach(pair -> {
				MatchNote match = currentTour - regulations.getLeaguePhaseMatches() == 0 ? pair.x : pair.y;
				res.add(match);
			});
			return res;
		}
		List<MatchNote> res = new ArrayList<>(playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2).size());
		playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2).forEach(pair -> {
			MatchNote match = currentTour - regulations.getLeaguePhaseMatches() % 2 == 0 ? pair.x : pair.y;
			res.add(match);
		});
		return res;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		if (currentTour < regulations.getLeaguePhaseMatches()) {
			return leaguePhaseOpponents.get(team);
		}

		List<Pair<MatchNote>> stage;
		if (regulations.getLeaguePhaseMatches() <= currentTour && regulations.getLeaguePhaseMatches() + 2 > currentTour) {
			stage = indirectPlayOffPairs;
		} else {
			stage = playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2);
		}

		List<MatchNote> res = new ArrayList<>(2);
		var pair = stage.stream().filter(p -> p.x.containsTeam(team)).findAny().get();
		res.add(pair.x);
		res.add(pair.y);
		return res;
	}

	@Override
	public void setTeams(List<String> teams) {
		if (teams.size() != regulations.getAmountOfTeams()) {
			throw new InvalidParameterException("invalid number of teams (cup - '" + name + "', setTeams)");
		}
		var newTable = new ArrayList<TournamentTableNote>(regulations.getAmountOfTeams());
		teams.forEach(t -> newTable.add(new TournamentTableNote(t)));
		leaguePhaseTable = newTable;
	}

	@Override
	public void setNextStagePairs(List<Pair<MatchNote>> pairs) {
		playOffPairs.add(pairs);
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
	public List<Pair<MatchNote>> currentStagePairs() {
		return playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2);
	}

	@Override
	public List<MatchNote> currentStageMatches() {
		if (currentTour < regulations.getLeaguePhaseMatches()) {
			List<MatchNote> res = new ArrayList<>(regulations.getAmountOfTeams() / 2);
			leaguePhaseOpponents.forEach((_, value) ->
				res.add(value.get(currentTour)));
			return res;
		}
		if (currentTour >= regulations.getLeaguePhaseMatches() && currentTour < regulations.getLeaguePhaseMatches() + 2) {
			List<MatchNote> res = new ArrayList<>(regulations.getIndirectPlayOffClubs() / 2);
			indirectPlayOffPairs.forEach(pair -> {
				MatchNote match = currentTour - regulations.getLeaguePhaseMatches() == 0 ? pair.x : pair.y;
				res.add(match);
			});
			return res;
		}

		List<MatchNote> res = new ArrayList<>(playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2).size());
		playOffPairs.get(currentTour - regulations.getLeaguePhaseMatches() - 2).forEach(pair -> {
			MatchNote match = currentTour - regulations.getLeaguePhaseMatches() % 2 == 0 ? pair.x : pair.y;
			res.add(match);
		});
		return res;
	}
}