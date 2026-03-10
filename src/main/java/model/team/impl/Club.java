package model.team.impl;

import model.footballer.IFootballerProfile;
import model.records.Coach;
import model.records.Stadium;
import model.squad.ISquad;
import model.team.ITeam;

import java.security.InvalidParameterException;
import java.util.*;

public class Club implements ITeam {
	private static final short AMOUNT_OF_NUMBERS = 99;
	private String name;
	private Stadium homeStadium;
    private Coach headCoach;
	private List<ISquad> defaultSquads;
    private List<IFootballerProfile> players;
    private int transferBudget;
	private ArrayList<Boolean> isNumbervalid;

	private boolean isNumberValid(short number) {
		if (number <= 0 || number >= 100) {
			return false;
		}
		return isNumbervalid.get(number - 1);
	}

	private List<Short> invalidNumbers(List<IFootballerProfile> players) {
		List<Boolean> found = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS + 1, false));
		List<Short> res = new ArrayList<>();
		for (var player : players) {
			if (found.get(player.getNumber()) || 0 >= player.getNumber() || 100 <= player.getNumber()) {
				res.add(player.getNumber());
			}
			found.set(player.getNumber() - 1, true);
		}
		return res;
	}


    public Club(String name, Stadium homeStadium, Coach headCoach, List<IFootballerProfile> players, int transferBudget)
	throws InvalidParameterException {
		List<Short> invalidNumbers = invalidNumbers(players);
		if (!invalidNumbers.isEmpty()) {
			throw new InvalidParameterException("invalid numbers in team '" + name + "': " + invalidNumbers);
		}
		isNumbervalid = new ArrayList<>(Collections.nCopies(AMOUNT_OF_NUMBERS, true));
        this.name = name;
		this.homeStadium = homeStadium;
		this.headCoach = headCoach;
		this.players = players;
        this.transferBudget = transferBudget;
		this.players.forEach(p -> {
			isNumbervalid.set(p.getNumber(), false);
		});
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void addPlayer(IFootballerProfile player) throws InvalidParameterException {
		if (!isNumberValid(player.getNumber())) {
			throw new InvalidParameterException("invalid number: " + player.getNumber());
		}
        players.add(player);
    }

    @Override
    public void setHeadCoach(Coach coach) {
        headCoach = coach;
    }

	@Override
	public List<ISquad> defaultSqauds() {
		return defaultSquads;
	}

	@Override
	public void addDefaultSquad(ISquad squad) {
		defaultSquads.add(squad);
	}

	@Override
    public List<IFootballerProfile> allPlayers() {
        return players;
    }

    @Override
    public Optional<IFootballerProfile> findPlayerByNumber(short number) {
        return players.stream().filter(p -> p.getNumber() == number).findAny();
    }

	@Override
	public Stadium homeStadion() {
		return homeStadium;
	}

	public void increaseTransferBudget(int add) {
		transferBudget += add;
	}

	public void decreaseTransferBudget(int loss) {
		transferBudget -= Math.min(loss, transferBudget);
	}

	public int transferBudget() {
		return transferBudget;
	}
}