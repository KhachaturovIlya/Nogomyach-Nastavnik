package model.service.squad.impl;

import lombok.Getter;
import lombok.Setter;
import model.footballer.IFootballerProfile;
import model.formation.Formation;
import model.service.squad.ISquadFactory;
import model.service.squad.ISquadService;
import model.squad.ISquad;
import model.enums.Role;
import model.team.ITeam;

import java.util.*;

public class SquadService implements ISquadService {
    private static final short PLAYERS_ON_FIELD = 11;
    private static final short MAX_PLAYERS_ON_BENCH = 11;
    private static final short MIN_PLAYERS_ON_BENCH = 9;

	@Setter
    private ITeam team;

    private short playersOnField;
    private short playersOnBench;
	@Getter
    private Map<Short, Role> startingXI;
	@Getter
    private ArrayList<Short> bench;

    private Optional<Formation> formation;
    private List<Role> freeRoles;
    private List<Role> occupiedRoles;

	private ISquadFactory factory;

    private void permutatePlayers() {
        startingXI.values().forEach(oldRole -> {
            var roleOptional = freeRoles.stream().filter(r -> r == oldRole).findAny();
            if (!roleOptional.isEmpty()) {
                freeRoles.remove(roleOptional.get());
                occupiedRoles.add(roleOptional.get());
            } else {
                Role newRole = Collections.min(freeRoles,
                        Comparator.comparingInt(x -> Math.abs(x.pos - oldRole.pos)));
                freeRoles.remove(newRole);
                occupiedRoles.add(newRole);
            }
        });
    }

	public SquadService(ISquadFactory factory) {
		this.factory = factory;
	}

    @Override
    public void addPlayerToStartingXI(short number, Role role) throws NoSuchElementException {
        boolean suchRoleFound = !freeRoles.stream().filter(r -> r == role).findAny().isEmpty();

        if (isFormationSet() && playersOnField < PLAYERS_ON_FIELD && suchRoleFound) {

            Optional<IFootballerProfile> profile = team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + team.getName() + "\""
                );
            }

            startingXI.put(number, role);
            playersOnField += 1;
        }
    }

    @Override
    public void addPlayerOnBench(short number) throws NoSuchElementException {
        if (playersOnBench < MAX_PLAYERS_ON_BENCH) {
            Optional<IFootballerProfile> profile = team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + team.getName() + "\""
                );
            }

            bench.add(number);
            playersOnBench += 1;
        }
    }

    @Override
    public void replacePlayerInStartingXI(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + team.getName() + "\""
            );
        }

		Role role = startingXI.get(numberOld);
        startingXI.remove(numberOld);
        startingXI.put(numberNew, role);
    }

    @Override
    public void replacePlayerOnBench(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + team.getName() + "\""
            );
        }

        bench.remove(numberOld);
        bench.add(numberNew);
    }

    @Override
    public void swapPlayersInStartingXI(short numberX, short numberY) {
        Role roleX = startingXI.get(numberX);
        Role roleY = startingXI.get(numberY);
        startingXI.replace(numberX, roleY);
        startingXI.replace(numberY, roleX);
    }

    @Override
    public void setFormation(Formation formation) {
        this.formation = Optional.of(formation);
        occupiedRoles.forEach(role -> freeRoles.add(role));
        occupiedRoles.clear();
        permutatePlayers();
    }

    @Override
    public boolean isFormationSet() {
        return !formation.isEmpty();
    }

    @Override
    public boolean isTeamReady() {
        return playersOnField == PLAYERS_ON_FIELD &&
                playersOnBench < MAX_PLAYERS_ON_BENCH &&
                playersOnBench > MIN_PLAYERS_ON_BENCH;
    }

	@Override
	public ISquad result() {
		if (isTeamReady()) {
			return factory.create(formation.get(), startingXI, bench);
		}
		return null;
	}

	@Override
	public void addSquadToChange(ISquad squad) {
		formation = Optional.of(squad.getFormation());
		startingXI = squad.getStartingXI();
		bench = squad.getBench();
		freeRoles.clear();
		Arrays.stream(formation.get().roles()).forEach(role -> occupiedRoles.add(role));
	}
}