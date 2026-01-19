package model.subclasses;

public enum Role {
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

    public final String description;

    Role(String description) {
        this.description = description;
    }
}