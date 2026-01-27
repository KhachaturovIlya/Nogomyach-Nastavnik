package model.entityImpls;

import model.entityInterfaces.*;

import java.util.List;
import java.util.Optional;

public class Club implements ITeam {
    private String _name;
    private ICoach _headCoach;
	private List<ISquad> _defaultSquads;
    private List<IFootballerProfile> _players;
    private int _transferBudget;

    public Club(String name, int transferBudget) {
        _name = name;
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

	public void increaseTransferBudget(int add) {
		_transferBudget += add;
	}

	public void decreaseTransferBudget(int loss) {
		_transferBudget -= loss;
	}

	public int transferBudget() {
		return _transferBudget;
	}
}