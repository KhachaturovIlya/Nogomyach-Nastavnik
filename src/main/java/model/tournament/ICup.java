package model.tournament;

import model.tournament.impl.MatchNote;

import java.util.List;

public interface ICup extends ITournament {
	short getCurrentStage();
	void increaseStage();
	void setPairsAfterDraw(List<MatchNote> pairsAfterDraw);
	void setTeams(List<String> teams);
}