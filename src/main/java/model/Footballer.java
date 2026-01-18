package model;

public interface Footballer extends Movable, Nameable, Teamable, Ageable {
    String role();
    void setRole(String role);

    int transferCost();
    void setTransferCost(int cost);

    int charasteristic(String characteristicName);
    void upgradeCharacteristic(String characteristicName, int difference);
}