package model.repoInterfaces;

import model.subclasses.Nationality;

public interface ICountryRepository {
	void addCountry(ICountry country);
	ICountry country(Nationality value);
}