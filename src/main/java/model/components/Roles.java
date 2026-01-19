package model.components;

public enum Roles {
    GK("GK"),
    LB("LB"),
    LCB("LCB"),
    RCB("RCB"),
    CB("CB"),
    RB("RB"),
    LM("LM"),
    CDM("CDM"),
    CM("CM"),
    CAM("CAM"),
    RM("RM"),
    LW("LW"),
    ST("ST"),
    CF("CF"),
    RW("RW");

    private final String _description;

    Roles(String description) {
        _description = description;
    }
}