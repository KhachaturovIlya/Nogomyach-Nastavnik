package model.subclasses;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public record LeagueRegulations(
		@JsonProperty("amount of teams") short amountOfTeams,
		@JsonProperty("teams to promote") short teamsToPromote,
		@JsonProperty("teams to eliminate") short teamsToEliminate
) {
	@JsonCreator
	public LeagueRegulations {}
}