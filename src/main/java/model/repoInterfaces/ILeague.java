package model.repoInterfaces;

import model.subclasses.TournamentTableNote;

import java.util.ArrayList;

public interface ILeague extends ITournament {
	ArrayList<TournamentTableNote> tournamentTable();
}