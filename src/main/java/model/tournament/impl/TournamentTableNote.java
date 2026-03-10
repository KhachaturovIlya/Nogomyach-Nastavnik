package model.tournament.impl;

import lombok.Getter;

@Getter
public class TournamentTableNote {
	private String teamName;
	private short matches;
	private short points;
	private short goalsConceded;
	private short goalsScored;
	private short cleanSheets;

	public TournamentTableNote(String teamName) {
		this.teamName = teamName;
	}

	public void increaseMatches() {
		matches += 1;
	}

	public void increasePoints(short add) {
		points += add;
	}

	public void increaseGoalsConceded(short add) {
		goalsConceded += add;
	}

	public void increaseGoalScored(short add) {
		goalsScored += add;
	}

	public void increaseCleanSheets() {
		cleanSheets += 1;
	}

	public void reset() {
		matches = 0;
		points = 0;
		goalsConceded = 0;
		goalsScored = 0;
		cleanSheets = 0;
	}
}