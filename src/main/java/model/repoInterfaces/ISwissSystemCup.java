package model.repoInterfaces;

import model.subclasses.MatchNote;
import model.subclasses.TournamentTableNote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface ISwissSystemCup extends ITournament {
	ArrayList<TournamentTableNote> leaguePhaseTable();
	void setTeams(List<String> teams);
	void setLeaguePhaseOpponents(Map<String, List<MatchNote>> opponents);
	void setIndirectPlayOffPairs(List<MatchNote> pairs);
	void setPlayOffPairs(List<MatchNote> pairs);
	short currentTour();
	void increaseTour();
	List<MatchNote> teamLeaguePhaseOpponents(String team);
	Map<String, List<MatchNote>> allTeamsLeaguePhaseOpponents();
	List<MatchNote> indirectPlayOffPairs();
	List<MatchNote> playOffPairs();
}