package model.servicesImpls;

import model.repoInterfaces.ISwissSystemCup;
import model.repoInterfaces.ITournament;
import model.servicesInterfaces.IDrawService;
import model.subclasses.MatchNote;
import model.subclasses.SwissSystemCupRegulations;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class SwissCupDrawService implements IDrawService {

	private void holdLeagueDraw(ISwissSystemCup cup) {
		var regulations = (SwissSystemCupRegulations) cup.regulations();
		Map<String, List<MatchNote>> leaguePhaseOpponents = new TreeMap<>();
		leaguePhaseOpponents.forEach((_, value) ->
			value = new ArrayList<>(regulations.leaguePhaseMatches()));


	}

	private void holdIndirectPlayOffDraw(ISwissSystemCup cup) {

	}

	private void holdPlayOffDraw(ISwissSystemCup cup) {

	}

	@Override
	public void holdADraw(ITournament tournament) {
		var cup = (ISwissSystemCup) tournament;
		if (cup.currentTour() == 1) {
			holdLeagueDraw(cup);
		} else if (cup.currentTour() == 9) {
			holdIndirectPlayOffDraw(cup);
		} else {
			holdPlayOffDraw(cup);
		}
	}
}