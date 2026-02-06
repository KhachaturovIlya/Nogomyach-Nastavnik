package model.repoInterfaces;

public interface ILocator {
	ICountryRepository countryRepository();
	ITeamRepository teamRepository();
	ITournamentRepository tournamentRepository();
}