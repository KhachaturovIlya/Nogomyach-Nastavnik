package model.repoImpls;

import model.repoInterfaces.ICountry;
import model.repoInterfaces.ICountryRepository;
import model.subclasses.Nationality;

import java.util.Map;
import java.util.TreeMap;

public class CountryRepository implements ICountryRepository {
	private Map<Nationality, ICountry> countries;

	public CountryRepository() {
		countries = new TreeMap<>();
	}

	@Override
	public void addCountry(ICountry country) {
		countries.put(country.nationality(), country);
	}

	@Override
	public ICountry country(Nationality value) {
		return countries.get(value);
	}
}