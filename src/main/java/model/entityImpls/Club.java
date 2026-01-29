package model.entityImpls;

import com.fasterxml.jackson.annotation.JsonCreator;
import model.entityInterfaces.*;
import model.subclasses.Stadium;

import java.util.List;
import java.util.Optional;

public class Club implements ITeam {
    private String _name;
	private Stadium _homeStadium;
    private ICoach _headCoach;
	private List<ISquad> _defaultSquads;
    private List<IFootballerProfile> _players;
    private int _transferBudget;

	@JsonCreator
    public Club(String name, Stadium homeStadium, ICoach headCoach, List<IFootballerProfile> players, int transferBudget) {
        _name = name;
		_homeStadium = homeStadium;
		_headCoach = headCoach;
		_players = players;
        _transferBudget = transferBudget;
    }

    @Override
    public String name() {
        return _name;
    }

    @Override
    public void addPlayer(IFootballerProfile player) {
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