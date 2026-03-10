package model.tournament.regulations.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.tournament.regulations.IRegulations;

@Getter
@AllArgsConstructor
public class LeagueRegulations implements IRegulations {
	private final short amountOfTeams;
	private final short teamsToPromote;
	private final short teamsToEliminate;
}