package model.tournament;

import model.tournament.impl.MatchNote;
import model.tournament.impl.TournamentTableNote;
import shared.Pair;

import java.util.List;
import java.util.Map;

public interface ISwissSystemCup extends ITournament {
	List<TournamentTableNote> getLeaguePhaseTable();
	void setTeams(List<String> teams);
	void setLeaguePhaseOpponents(Map<String, List<MatchNote>> opponents);
	void setIndirectPlayOffPairs(List<Pair<MatchNote>> pairs);
	void setNextStagePairs(List<Pair<MatchNote>> pairs);
	short getCurrentTour();
	void increaseTour();
	List<MatchNote> teamLeaguePhaseOpponents(String team);
	Map<String, List<MatchNote>> allTeamsLeaguePhaseOpponents();
	List<Pair<MatchNote>> getIndirectPlayOffPairs();
	List<Pair<MatchNote>> currentStagePairs();
}