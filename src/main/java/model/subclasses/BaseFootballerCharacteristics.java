package model.subclasses;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BaseFootballerCharacteristics {
    private List<Short> _characteristics = new ArrayList<>();

	private boolean isCharacteristicValid(short characteristic) {
		return 0 >= characteristic && characteristic <= 100;
	}

	public BaseFootballerCharacteristics(ArrayList<Short> characteristics) {
		_characteristics = characteristics;
	}

	@JsonCreator
	public BaseFootballerCharacteristics(@JsonProperty("characteristics")Map<String, Short> characteristics)
	throws InvalidParameterException {
		characteristics.forEach((key, value) -> {
			if (!isCharacteristicValid(value)) {
				throw new InvalidParameterException("invalid characteristic: " + key + " - " + value);
			}
			int pos = FootballerCharacteristicsEnum.valueOf(key).array_pos;
			_characteristics.set(pos, value);
		});
	}

    public short characteristic(FootballerCharacteristicsEnum characteristic) {
        return _characteristics.get(characteristic.array_pos);
    }

    public void increaseCharacteristic(FootballerCharacteristicsEnum characteristic, short add) {
		short currentValue = _characteristics.get(characteristic.array_pos);
		_characteristics.set(characteristic.array_pos, (short) Math.min(currentValue + add, 100));
    }

    public void decreaseCharacteristic(FootballerCharacteristicsEnum characteristic, short loss) {
		short currentValue = _characteristics.get(characteristic.array_pos);
		_characteristics.set(characteristic.array_pos, (short) Math.max(currentValue - loss, 0));
    }
}