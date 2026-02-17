package model.repository;

public interface ILocator {
	ICountryRepository getCountryRepository();
	ITeamRepository getTeamRepository();
	ITournamentRepository getTournamentRepository();
}