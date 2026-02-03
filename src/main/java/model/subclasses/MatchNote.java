package model.subclasses;

import shared.Pair;

import java.security.InvalidParameterException;
import java.time.LocalDate;

public class MatchNote {
	private String teamX;
	private String teamY;
	private short goalsTeamX;
	private short goalsTeamY;
	private LocalDate date;

	public MatchNote(String teamX, String teamY, LocalDate date) {
		this.teamX = teamX;
		this.teamY = teamY;
		this.date = date;
	}

	public MatchNote(String teamX, String teamY) {
		this.teamX = teamX;
		this.teamY = teamY;
	}

	public Pair<Short> score() {
		return new Pair<>(goalsTeamX, goalsTeamY);
	}

	public void addTeamXGoals(short goals) {
		goalsTeamX += goals;
	}

	public void addTeamYGoals(short goals) {
		goalsTeamY += goals;
	}

	public String teamX() {
		return teamX;
	}

	public String teamY() {
		return teamY;
	}

	public void changeDate(LocalDate newDate) {
		date = newDate;
	}

	public LocalDate date() {
		return date;
	}

	public boolean containsTeam(String team) {
		return teamX.equals(team) || teamY.equals(team);
	}

	public String teamOpponent(String team) {
		if (!containsTeam(team)) {
			throw new InvalidParameterException(
					"team '" + team +"' does not participate in this match (MatchNote::teamOpponent)");
		}
		if (teamY.equals(team)) {
			return teamX;
		}
		return teamY;
	}
}