package model.footballer;

public class FootballerMatchInfo {
    private String team;
    private short number;
    private boolean onField;
    private boolean onBench;

    public FootballerMatchInfo(String team, short number, boolean onField) {
        this.team = team;
        this.number = number;
        this.onField = onField;
        onBench = !onField;
    }

    String team() {
        return team;
    }

    short number() {
        return number;
    }

    boolean onField() {
        return onField;
    }

    boolean onBench() {
        return onBench;
    }

    void reset(String team, short number, boolean onField) {
        this.team = team;
        this.number = number;
        this.onField = onField;
        onBench = !onField;
    }
}