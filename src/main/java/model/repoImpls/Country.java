package model.repoImpls;

import model.repoInterfaces.ICountry;
import model.repoInterfaces.INationalLeague;
import model.subclasses.Nationality;

import java.util.ArrayList;
import java.util.List;

public class Country implements ICountry {
	private Nationality country;
	private List<INationalLeague> leagues = new ArrayList<>();



	public Country(Nationality country) {
		this.country = country;
	}

	public Country(String country) {
		this.country = Nationality.fromString(country);
	}

	@Override
	public Nationality nationality() {
		return country;
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