package model.footballer.impl;

import lombok.Getter;
import lombok.Setter;
import model.enums.FootballerCharacteristicsEnum;
import model.enums.Role;
import model.footballer.BaseFootballerCharacteristics;
import model.footballer.IPlayingFootballer;
import shared.Vector3;

public class PlayingFootballer implements IPlayingFootballer {
    private BaseFootballerCharacteristics characteristics;
	@Getter @Setter
    private Vector3 speed = new Vector3(0, 0, 0);
	@Getter @Setter
    private Vector3 acceleration = new Vector3(0, 0, 0);

	@Getter @Setter
    private double stamina = 1.0;
	@Getter @Setter
    private short ID;

	@Getter @Setter
    private Role role;
	@Getter
    private Vector3 size;
	@Getter @Setter
    private Vector3 position;

    public PlayingFootballer(BaseFootballerCharacteristics characteristics, Role role) {
        this.characteristics = characteristics;
        this.role = role;

        // пока хз что сделать с размером футболиста
        size = new Vector3(0.25, 0.25, characteristics.characteristic(FootballerCharacteristicsEnum.HEIGHT));
    }

    @Override
    public void increaseStamina(double add) {
        stamina += add;
    }

    @Override
    public void decreaseStamina(double loss) {
        stamina -= loss;
    }

    @Override
    public short getID() {
        return ID;
    }

    @Override
    public void setID(short ID) {
        this.ID = ID;
    }

    @Override
    public short charasteristic(FootballerCharacteristicsEnum characteristic) {
        return characteristics.characteristic(characteristic);
    }

    @Override
    public double width() {
        return size.x;
    }

    @Override
    public double length() {
        return size.y;
    }

    @Override
    public double height() {
        return size.z;
    }

    @Override
    public void move(Vector3 shift) {
        position.addLocal(shift);
    }

    @Override
    public void increaseSpeed(Vector3 speedAdd) {
        speed.addLocal(speedAdd);
    }

    @Override
    public void decreaseSpeed(Vector3 speedLoss) {
        speedLoss.mulLocal(-1.0);
        speed.addLocal(speedLoss);
    }
}