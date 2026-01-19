package model.entityImplementations;

import model.entityInterfaces.ICoach;

public class Coach implements ICoach {
    private String _name;
    private String _club;
    private String _nationality;
    private short _age;

    public Coach(String name, String club, String nationality, short age) {
        _name = name;
        _club = club;
        _nationality = nationality;
        _age = age;
    }

    @Override
    public String club() {
        return _club;
    }

    @Override
    public void setClub(String club) {
        _club = club;
    }

    @Override
    public short age() {
        return _age;
    }

    @Override
    public void increaseAge() {
        _age += 1;
    }

    @Override
    public String name() {
        return _name;
    }

    @Override
    public String nationality() {
        return _nationality;
    }
}