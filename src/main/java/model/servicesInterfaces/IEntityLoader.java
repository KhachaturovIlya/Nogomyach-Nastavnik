package model.servicesInterfaces;

import java.io.IOException;
import java.nio.file.Path;

public interface IEntityLoader {
	void load(Path configPath) throws IOException;
}