package model.tournament.impl;

import lombok.Getter;
import model.tournament.regulations.impl.DefaultCupRegulations;
import model.tournament.ICup;
import shared.IsPowerOfTwo;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class DefaultCup implements ICup {
	@Getter
	private final String name;
	@Getter
	private DefaultCupRegulations regulations;
	@Getter
	private short currentStage;
	@Getter
	private List<String> teams;
	private List<List<MatchNote>> pairsAfterDraw;


	public DefaultCup(String name, DefaultCupRegulations regulations, List<String> teams) throws InvalidParameterException {
		if (!IsPowerOfTwo.check(regulations.getAmountOfTeams())) {
			throw new InvalidParameterException("amount of teams in cup must be power of 2 (cup '" + name + "')");
		}
		if (regulations.getAmountOfTeams() != teams.size()) {
			throw new InvalidParameterException("number of teams in regulations and in reality are different (cup '" + name + "')");
		}
		this.name = name;
		this.regulations = regulations;
		this.teams = teams;
		pairsAfterDraw = new ArrayList<>((int) (Math.log(regulations.getAmountOfTeams()) / Math.log(2)));
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
	public List<MatchNote> nextStageMatches() {
		return pairsAfterDraw.get(currentStage);
	}

	@Override
	public List<MatchNote> allTeamMatches(String team) {
		return List.of(pairsAfterDraw.get(currentStage).stream().filter(p -> p.containsTeam(team)).findAny().get());
	}

	@Override
	public List<MatchNote> currentStageMatches() {
		return pairsAfterDraw.get(currentStage);
	}

	@Override
	public void increaseStage() {
		++currentStage;
	}

	@Override
	public void setPairsAfterDraw(List<MatchNote> pairsAfterDraw) {
		this.pairsAfterDraw.add(pairsAfterDraw);
	}

	@Override
	public void setTeams(List<String> teams) {
		if (teams.size() != regulations.getAmountOfTeams()) {
			throw new InvalidParameterException("invalid number of teams (cup - '" + name + "', setTeams)");
		}
		this.teams = teams;
	}
}