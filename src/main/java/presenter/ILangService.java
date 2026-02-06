package presenter;

import java.io.IOException;
import java.nio.file.Path;

public interface ILangService {
    String getText(String textId);
    void loadFile(Path path) throws IOException;
    void resetMap();
}
