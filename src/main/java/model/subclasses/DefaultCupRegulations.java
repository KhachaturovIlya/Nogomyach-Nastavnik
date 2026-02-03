package model.subclasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultCupRegulations implements IRegulations {
	private short _amountOfTeams;

	@JsonCreator
	public DefaultCupRegulations(@JsonProperty("amount of teams")short amountOfTeams) {
		_amountOfTeams = amountOfTeams;
	}

	@Override
	public short amountOfTeams() {
		return _amountOfTeams;
	}
}