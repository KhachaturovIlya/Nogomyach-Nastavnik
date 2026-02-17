package model.repository.impl;

import lombok.NoArgsConstructor;
import model.repository.ITeamRepository;
import model.team.ITeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor
public class TeamRepository implements ITeamRepository {
	private Map<String, ITeam> teams = new HashMap<>();


	@Override
	public void addTeam(ITeam team) {
		teams.put(team.getName(), team);
	}

	@Override
	public ITeam teamByName(String name) {
		return teams.get(name);
	}

	@Override
	public List<ITeam> allTeams() {
		return List.of((ITeam) teams.values());
	}
}