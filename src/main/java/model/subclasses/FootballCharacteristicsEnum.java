package model.subclasses;

public enum FootballCharacteristicsEnum {
    WEIGHT("weight"),
    HEIGHT("height"),

    SHORTPASS("shortPass"),
    LONGPASS("longPass"),
    CROSSES("crosses"),

    VISION("vision"),
    POSITION_CHOOSING("positionChoosing"),

    CONFIDENCE("confidence"),
    DISCIPLINE("discipline"),
    COMPOSURE("composure"),

    FINISHING("finishing"),
    LONG_SHOOTING("longShooting"),

    DRIBBLING("dribbling"),
    TECHNIQUE("technique"),

    TACKLE("tackle"),
    INTERCEPTION("interception"),

    POWER("power"),
    STAMINA("stamina"),
    SPRINT_SPEED("sprintSpeed"),
    HEADPLAY("headPlay"),

    REACTION("reaction"),
    JUMPING("jumping"),
    PLAYING_OUT("playingOut"),
    CROSS_INTERCEPTION("crossInterception");

    private final String _string;
    FootballCharacteristicsEnum(String string) {
        _string = string;
    }
}