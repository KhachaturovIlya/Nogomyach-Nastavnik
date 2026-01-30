package model.entityImpls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.entityInterfaces.*;
import model.subclasses.Stadium;

import java.security.InvalidParameterException;
import java.util.*;

public class Club implements ITeam {
	private static final short AMOUNT_OF_NUMBERS = 99;
	private String _name;
	private Stadium _homeStadium;
    private ICoach _headCoach;
	private List<ISquad> _defaultSquads;
    private List<IFootballerProfile> _players;
    private int _transferBudget;
	private ArrayList<Boolean> _isNumbervalid;

	private boolean isNumberValid(short number) {
		if (0 >= number || 100 <= number) {
			return false;
		}
		return _isNumbervalid.get(number - 1);
	}

	private List<Short> invalidNumbers(List<IFootballerProfile> players) {
		List<Boolean> found = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS, true));
		List<Short> res = new ArrayList<>();
		for (var player : players) {
			if (found.get(player.number()) || 0 >= player.number() || 100 <= player.number()) {
				res.add(player.number());
			}
			found.set(player.number(), true);
		}
		return res;
	}


	@JsonCreator
    public Club(@JsonProperty("name")String name, @JsonProperty("home stadium")Stadium homeStadium,
	@JsonProperty("head coach")ICoach headCoach, @JsonProperty("players")List<IFootballerProfile> players,
	@JsonProperty("transfer budget")int transferBudget)
	throws InvalidParameterException {
		List<Short> invalidNumbers = invalidNumbers(players);
		if (!invalidNumbers.isEmpty()) {
			throw new InvalidParameterException("invalid numbers in team '" + name + "': " + invalidNumbers);
		}
		_isNumbervalid = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS, true));
        _name = name;
		_homeStadium = homeStadium;
		_headCoach = headCoach;
		_players = players;
        _transferBudget = transferBudget;
		_players.forEach(p -> {
			_isNumbervalid.set(p.number(), false);
		});
    }

    @Override
    public String name() {
        return _name;
    }

    @Override
    public void addPlayer(IFootballerProfile player) throws InvalidParameterException {
		if (!isNumberValid(player.number())) {
			throw new InvalidParameterException("invalid number: " + player.number());
		}
        _players.add(player);
    }

    @Override
    public void setHeadCoach(ICoach coach) {
        _headCoach = coach;
    }

	@Override
	public List<ISquad> defaultSqauds() {
		return _defaultSquads;
	}

	@Override
	public void addDefaultSquad(ISquad squad) {
		_defaultSquads.add(squad);
	}

	@Override
    public List<IFootballerProfile> allPlayers() {
        return _players;
    }

    @Override
    public Optional<IFootballerProfile> findPlayerByNumber(short number) {
        return _players.stream().filter(p -> p.number() == number).findAny();
    }

	@Override
	public Stadium homeStadion() {
		return _homeStadium;
	}

	public void increaseTransferBudget(int add) {
		_transferBudget += add;
	}

	public void decreaseTransferBudget(int loss) {
		_transferBudget -= Math.min(loss, _transferBudget);
	}

	public int transferBudget() {
		return _transferBudget;
	}
}