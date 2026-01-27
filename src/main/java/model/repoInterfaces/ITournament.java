package model.repoInterfaces;

import model.components.IHasName;
import model.entityInterfaces.ITeam;

public interface ITournament extends IHasName {
	void addTeam(ITeam team);
	void removeTeam(String teamName);
	void resetResults();
}