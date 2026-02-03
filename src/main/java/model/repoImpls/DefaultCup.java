package model.repoImpls;

import model.repoInterfaces.ITournament;
import model.subclasses.DefaultCupRegulations;
import model.subclasses.IRegulations;
import model.subclasses.MatchNote;
import shared.IsPowerOfTwo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DefaultCup implements ITournament {
	private final String name;
	private DefaultCupRegulations regulations;
	private List<String> teams;
	private List<MatchNote> pairsAfterDraw;


	public DefaultCup(String name, DefaultCupRegulations regulations, List<String> teams) throws InvalidParameterException {
		if (!IsPowerOfTwo.check(regulations.amountOfTeams())) {
			throw new InvalidParameterException("amount of teams in cup must be power of 2 (cup '" + name + "')");
		}
		if (regulations.amountOfTeams() != teams.size()) {
			throw new InvalidParameterException("number of teams in regulations and in reality are different (cup '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		this.teams = teams;
		pairsAfterDraw = new ArrayList<>(regulations.amountOfTeams() / 2);
	}

	@Override
	public void resetResults() {
		pairsAfterDraw.clear();
		teams.clear();
	}

	@Override
	public void replaceTeam(String oldTeam, String newTeam) {
		if (!pairsAfterDraw.isEmpty()) {
			throw new IllegalStateException("tournament has been already started (cup - '" + name + "', replaceTeam)");
		}
		teams.remove(oldTeam);
		teams.add(newTeam);
	}

	@Override
	public List<String> teams() {
		return teams;
	}

	@Override
	public IRegulations regulations() {
		return regulations;
	}

	@Override
	public List<MatchNote> nextStageMatches() {
		return pairsAfterDraw;
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		return List.of(pairsAfterDraw.stream().filter(p -> p.containsTeam(team)).findAny().get());
	}

	@Override
	public String name() {
		return name;
	}

	public List<MatchNote> pairsAfterDraw() {
		return pairsAfterDraw;
	}

	public void setPairsAfterDraw(List<MatchNote> pairsAfterDraw) {
		this.pairsAfterDraw = pairsAfterDraw;
	}

	public void setTeams(List<String> teams) {
		if (teams.size() != regulations.amountOfTeams()) {
			throw new InvalidParameterException("invalid number of teams (cup - '" + name + "', setTeams)");
		}
		this.teams = teams;
	}
}