package model.subclasses;


public class SwissSystemCupRegulations implements IRegulations {
	private short _leaguePhaseMembers;
	private short _leaguePhaseMatches;
	private short _directPlayOffClubs;
	private short _indirectPlayOffClubs;


	public SwissSystemCupRegulations(short leaguePhaseMembers, short leaguePhaseMatches,
	short directPlayOffClubs, short indirectPlayOffClubs) {
		_leaguePhaseMembers = leaguePhaseMembers;
		_leaguePhaseMatches = leaguePhaseMatches;
		_directPlayOffClubs = directPlayOffClubs;
		_indirectPlayOffClubs = indirectPlayOffClubs;
	}

	@Override
	public short amountOfTeams() {
		return _leaguePhaseMembers;
	}

	public short leaguePhaseMatches() {
		return _leaguePhaseMatches;
	}

	public short directPlayOffClubs() {
		return _directPlayOffClubs;
	}

	public short indirectPlayOffClubs() {
		return _indirectPlayOffClubs;
	}

	public short playOfParticipantsNumber() {
		return (short) (_directPlayOffClubs + _indirectPlayOffClubs / 2);
	}
}