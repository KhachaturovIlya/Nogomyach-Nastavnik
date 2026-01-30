package model.servicesImpls;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import model.entityImpls.Club;
import model.entityInterfaces.ITeam;
import model.repoImpls.Country;
import model.repoImpls.NationalLeague;
import model.repoInterfaces.ICountry;
import model.repoInterfaces.ILeague;
import model.servicesInterfaces.IEntityLoader;
import model.subclasses.LeagueRegulations;
import model.subclasses.Nationality;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.stream.Stream;

public class JsonEntityLoader implements IEntityLoader {
	private ObjectMapper _mapper;
	private Path _srcPath;
	private Map<Nationality, ICountry> _countries;


	private ITeam loadClub(Path teamConfigPath) throws IOException, InvalidParameterException {
		return _mapper.readValue(
			_srcPath.resolve(teamConfigPath).toFile(),
			new TypeReference<Club>() {}
		);
	}

	private ILeague loadLeague(Path leagueConfigPath) throws IOException {
		LeagueRegulations regulations = _mapper.readValue(
			_srcPath.resolve(leagueConfigPath + "regulations.json").toFile(),
			new TypeReference<>() {});
		ILeague league = new NationalLeague(leagueConfigPath.getFileName().toString(), regulations);

		try(Stream<Path> clubs = Files.list(_srcPath.resolve(leagueConfigPath.toString() + "clubs"))) {
			clubs.forEach(club -> {
				try {
					ITeam team = loadClub(club);
					league.addTeam(team);
				} catch (IOException exception) {
					System.err.println("invalid config file: " + exception.getMessage());
					throw new RuntimeException(exception);
				}
			});
			return league;
		} catch (Exception exception) {
			System.err.println("Could not read config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}

	private ICountry loadCountry(Path countryConfigPath) throws IOException {
		try(Stream<Path> leagues = Files.list(_srcPath.resolve(countryConfigPath))) {
			ICountry country = new Country(countryConfigPath.getFileName().toString());

			leagues.forEach(path -> {
				try {
					ILeague league = loadLeague(path);
					country.addLeague(league);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

			return country;
		} catch (Exception exception) {
			System.err.println("invalid config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}


	public JsonEntityLoader(Path srcPath, Map<Nationality, ICountry> countries) {
		_mapper = new ObjectMapper();
		_srcPath = srcPath;
		_countries = countries;
	}

	@Override
	public void load(Path configPath) throws IOException {
		try(Stream<Path> countries = Files.list(configPath)) {
			countries.forEach(path -> {
				try {
					ICountry country = loadCountry(path);
					_countries.put(country.nationality(), country);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			});

		} catch (Exception exception) {
			System.err.println("Could not read config file: " + exception.getMessage());
			throw new IOException(exception);
		}
	}
}