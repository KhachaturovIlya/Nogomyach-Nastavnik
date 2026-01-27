package model.repoImpls;

import model.repoInterfaces.ICountry;
import model.repoInterfaces.ILeague;
import model.subclasses.Nationality;

import java.util.List;

public class Country implements ICountry {
	private Nationality _country;
	private List<ILeague> _leagues;



	public Country(Nationality country) {
		_country = country;
	}

	@Override
	public Nationality nationality() {
		return _country;
	}

	@Override
	public ILeague premierLeague() {
		return _leagues.getFirst();
	}

	@Override
	public ILeague divisionNumber(short number) {
		return _leagues.get(number);
	}

	@Override
	public void addLeague(ILeague league) {
		_leagues.add(league);
	}
}