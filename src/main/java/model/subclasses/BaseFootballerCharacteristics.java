package model.subclasses;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

public class BaseFootballerCharacteristics {
    private ArrayList<Short> _characteristics;

	public BaseFootballerCharacteristics(ArrayList<Short> characteristics) {
		_characteristics = characteristics;
	}

	@JsonCreator
	public BaseFootballerCharacteristics(Map<String, Short> characteristics) {
		characteristics.forEach((key, value) -> {
			int pos = FootballerCharacteristicsEnum.valueOf(key).array_pos;
			_characteristics.set(pos, value);
		});
	}

    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return _characteristics.get(characteristic.array_pos);
    }

    public void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add) {
        _characteristics.set(characteristic.array_pos, (short) (_characteristics.get(characteristic.array_pos) + add));
    }

    public void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss) {
		short currentValue = _characteristics.get(characteristic.array_pos);
		_characteristics.set(characteristic.array_pos, (short) (currentValue - Math.min(currentValue, loss)));
    }
}