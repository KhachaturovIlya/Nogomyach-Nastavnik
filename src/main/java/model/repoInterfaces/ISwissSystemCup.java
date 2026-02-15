package model.repoInterfaces;

import model.subclasses.MatchNote;
import model.subclasses.TournamentTableNote;
import shared.Pair;

import java.util.List;
import java.util.Map;

public interface ISwissSystemCup extends ITournament {
	List<TournamentTableNote> leaguePhaseTable();
	void setTeams(List<String> teams);
	void setLeaguePhaseOpponents(Map<String, List<MatchNote>> opponents);
	void setIndirectPlayOffPairs(List<Pair<MatchNote>> pairs);
	void setNextStagePairs(List<Pair<MatchNote>> pairs);
	short currentTour();
	void increaseTour();
	List<MatchNote> teamLeaguePhaseOpponents(String team);
	Map<String, List<MatchNote>> allTeamsLeaguePhaseOpponents();
	List<Pair<MatchNote>> indirectPlayOffPairs();
	List<Pair<MatchNote>> currentStagePairs();
}