package model.repository.impl;

import lombok.NoArgsConstructor;
import model.country.ICountry;
import model.enums.Nationality;
import model.repository.ICountryRepository;

import java.util.Map;
import java.util.TreeMap;

@NoArgsConstructor
public class CountryRepository implements ICountryRepository {
	private Map<Nationality, ICountry> countries = new TreeMap<>();

	@Override
	public void addCountry(ICountry country) {
		countries.put(country.getNationality(), country);
	}

	@Override
	public ICountry country(Nationality value) {
		return countries.get(value);
	}
}