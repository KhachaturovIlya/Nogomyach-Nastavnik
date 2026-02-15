package model.repoInterfaces;

import model.components.IHasName;
import model.subclasses.IRegulations;
import model.subclasses.MatchNote;

import java.util.List;

public interface ITournament extends IHasName {
	void resetResults();
	void replaceTeam(String oldTeam, String newTeam);
	List<String> teams();
	IRegulations regulations();
	List<MatchNote> nextStageMatches();
	List<MatchNote> allTeamMatches(String team);
	List<MatchNote> currentStageMatches();
}