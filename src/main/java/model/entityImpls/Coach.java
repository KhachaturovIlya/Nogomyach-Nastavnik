package model.entityImpls;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import model.entityInterfaces.ICoach;
import model.subclasses.Nationality;

import java.time.LocalDate;

public class Coach implements ICoach {
    private String _name;
    private Nationality _nationality;
    private LocalDate _dateOfBirth;

	@JsonCreator
    public Coach(String name, Nationality nationality, @JsonProperty("date of birth")LocalDate dateOfBirth) {
        _name = name;
        _nationality = nationality;
        _dateOfBirth = dateOfBirth;
    }

    @Override
    public String name() {
        return _name;
    }

    @Override
    public Nationality nationality() {
        return _nationality;
    }

	@Override
	public LocalDate dateOfBirth() {
		return _dateOfBirth;
	}
}