package model.tournament.regulations.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import model.tournament.regulations.IRegulations;

@AllArgsConstructor
public class DefaultCupRegulations implements IRegulations {
	@Getter private short amountOfTeams;
}