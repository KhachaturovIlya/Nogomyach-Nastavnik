package model.entityInterfaces;

import model.components.IHasName;
import model.entityImpls.Coach;
import model.subclasses.Stadium;

import java.util.List;
import java.util.Optional;

public interface ITeam extends IHasName {
    void addPlayer(IFootballerProfile player);
    void setHeadCoach(Coach coach);
	List<ISquad> defaultSqauds();
	void addDefaultSquad(ISquad squad);
    List<IFootballerProfile> allPlayers();
    Optional<IFootballerProfile> findPlayerByNumber(short number);
	Stadium homeStadion();
}