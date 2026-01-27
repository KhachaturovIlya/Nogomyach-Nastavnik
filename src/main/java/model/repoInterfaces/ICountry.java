package model.repoInterfaces;

import model.components.IHasNationality;

public interface ICountry extends IHasNationality {
	ILeague premierLeague();
	ILeague divisionNumber(short number);
	void addLeague(ILeague league);
}