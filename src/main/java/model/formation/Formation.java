package model.formation;

import model.enums.Role;

public record Formation (
		String textDescription,
		Role[] roles
) {}