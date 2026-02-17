package model.repository.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import model.repository.ICountryRepository;
import model.repository.ILocator;
import model.repository.ITeamRepository;
import model.repository.ITournamentRepository;

@NoArgsConstructor
@Getter
public class Locator implements ILocator {
	private ICountryRepository countryRepository = new CountryRepository();
	private ITournamentRepository tournamentRepository = new TournamentRepository();
	private ITeamRepository teamRepository = new TeamRepository();
}