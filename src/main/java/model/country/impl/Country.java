package model.country.impl;

import lombok.Getter;
import model.country.ICountry;
import model.tournament.INationalLeague;
import model.enums.Nationality;

import java.util.ArrayList;
import java.util.List;

public class Country implements ICountry {
	@Getter
	private Nationality nationality;
	private List<INationalLeague> leagues = new ArrayList<>();



	public Country(Nationality country) {
		nationality = country;
	}

	public Country(String country) {
		nationality = Nationality.fromString(country);
	}

	@Override
	public INationalLeague premierLeague() {
		return leagues.getFirst();
	}

	@Override
	public INationalLeague divisionByNumber(short number) {
		return leagues.get(number);
	}

	@Override
	public void addLeague(INationalLeague league) {
		leagues.add(league);
	}
}