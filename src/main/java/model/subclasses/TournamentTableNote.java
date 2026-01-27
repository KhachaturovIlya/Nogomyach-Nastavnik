package model.subclasses;

import model.entityInterfaces.ITeam;

public class TournamentTableNote {
	private ITeam _team;
	private short _points;
	private short _goalsConceded;
	private short _goalsScored;
	private short _cleanSheets;

	public TournamentTableNote(ITeam team) {
		_team = team;
	}

	public ITeam team() {
		return _team;
	}

	public String teamName() {
		return _team.name();
	}

	public short points() {
		return _points;
	}

	public void increasePoints(short add) {
		_points += add;
	}

	public short goalsConceded() {
		return _goalsConceded;
	}

	public void increaseGoalsConceded(short add) {
		_goalsConceded += add;
	}

	public short goalsScored() {
		return _goalsScored;
	}

	public void increaseGoalScored(short add) {
		_goalsScored += add;
	}

	public short cleanSheets() {
		return _cleanSheets;
	}

	public void increaseCleanSheets() {
		_cleanSheets += 1;
	}

	public void reset() {
		_points = 0;
		_goalsConceded = 0;
		_goalsScored = 0;
		_cleanSheets = 0;
	}
}