package model.subclasses;

import com.fasterxml.jackson.annotation.JsonCreator;

public record Stadium(
		String name,
		int capacity
) {
	@JsonCreator
	public Stadium {}
}