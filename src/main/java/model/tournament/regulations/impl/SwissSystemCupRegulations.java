package model.tournament.regulations.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.tournament.regulations.IRegulations;

@Getter
@AllArgsConstructor
public class SwissSystemCupRegulations implements IRegulations {
	private short leaguePhaseMembers;
	private short leaguePhaseMatches;
	private short pots;
	private short directPlayOffClubs;
	private short indirectPlayOffClubs;

	public short playOfParticipantsNumber() {
		return (short) (directPlayOffClubs + indirectPlayOffClubs / 2);
	}

	@Override
	public short getAmountOfTeams() {
		return leaguePhaseMembers;
	}
}