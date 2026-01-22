package model.servicesImpls;

import model.entityInterfaces.IFootballerProfile;
import model.entityInterfaces.IFormation;
import model.entityInterfaces.IPlayingFootballer;
import model.entityInterfaces.ITeam;
import model.servicesInterfaces.IMatchStartService;
import model.servicesInterfaces.IPlayingFootballerFactory;
import model.subclasses.Role;

import java.util.*;

public class MatchStartService implements IMatchStartService {
    private static final short PLAYERS_ON_FIELD = 11;
    private static final short MAX_PLAYERS_ON_BENCH = 11;
    private static final short MIN_PLAYERS_ON_BENCH = 9;

    private ITeam _team;

    private short _playersOnField;
    private short _playersOnBench;
    private Map<Short, IPlayingFootballer> _startingXI;
    private Map<Short, IPlayingFootballer> _bench;

    private Optional<IFormation> _formation;
    private List<Role> _freeRoles;
    private List<Role> _occupiedRoles;

    private IPlayingFootballerFactory _factory;

    private void permutatePlayers() {
        _startingXI.values().forEach(player -> {
            var roleOptional = _freeRoles.stream().filter(r -> r == player.role()).findAny();
            if (!roleOptional.isEmpty()) {
                _freeRoles.remove(roleOptional.get());
                _occupiedRoles.add(roleOptional.get());
            } else {
                Role role = Collections.min(_freeRoles,
                        Comparator.comparingInt(x -> Math.abs(x.pos - player.role().pos)));
                player.setRole(role);
                _freeRoles.remove(role);
                _occupiedRoles.add(role);
            }
        });
    }



    public MatchStartService(IPlayingFootballerFactory factory) {
        _factory = factory;
    }

    public void setTeam(ITeam team) {
        _team = team;
    }

    @Override
    public void addPlayerToStartingXI(short number, Role role) throws NoSuchElementException {


        if (_playersOnField < PLAYERS_ON_FIELD) {

            Optional<IFootballerProfile> profile = _team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + _team.name() + "\""
                );
            }

            var player = _factory.produce(profile.get(), role);
            _startingXI.put(number, player);
            _playersOnField += 1;
        }
    }

    @Override
    public void addPlayerOnBench(short number) throws NoSuchElementException {
        if (_playersOnBench < MAX_PLAYERS_ON_BENCH) {
            Optional<IFootballerProfile> profile = _team.findPlayerByNumber(number);

            if (profile.isEmpty()) {
                throw new NoSuchElementException(
                        "no footballer with " + number + "number in team \"" + _team.name() + "\""
                );
            }

            var player = _factory.produce(profile.get());
            _bench.put(number, player);
            _playersOnBench += 1;
        }
    }

    @Override
    public void replacePlayerInStartingXI(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = _team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + _team.name() + "\""
            );
        }

        var player = _factory.produce(profile.get());
        _startingXI.remove(numberOld);
        _startingXI.put(numberNew, player);
    }

    @Override
    public void replacePlayerOnBench(short numberOld, short numberNew) throws NoSuchElementException {
        Optional<IFootballerProfile> profile = _team.findPlayerByNumber(numberNew);

        if (profile.isEmpty()) {
            throw new NoSuchElementException(
                    "no footballer with " + numberNew + "number in team \"" + _team.name() + "\""
            );
        }

        var player = _factory.produce(profile.get());
        _bench.remove(numberOld);
        _bench.put(numberNew, player);
    }

    @Override
    public void swapPlayersInStartingXI(short numberX, short numberY) {
        Role roleX = _startingXI.get(numberX).role();
        Role roleY = _startingXI.get(numberY).role();
        _startingXI.get(numberX).setRole(roleY);
        _startingXI.get(numberY).setRole(roleX);
    }

    @Override
    public void setFormation(IFormation formation) {
        _formation = Optional.of(formation);
        _occupiedRoles.forEach(role -> _freeRoles.add(role));
        _occupiedRoles.clear();
        permutatePlayers();
    }

    @Override
    public boolean isFormationSet() {
        return !_formation.isEmpty();
    }
}