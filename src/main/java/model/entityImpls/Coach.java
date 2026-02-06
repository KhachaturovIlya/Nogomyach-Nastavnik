package model.entityImpls;

import model.subclasses.Nationality;

import java.time.LocalDate;

public record Coach (
    String name,
    Nationality nationality,
    LocalDate dateOfBirth
) {}
