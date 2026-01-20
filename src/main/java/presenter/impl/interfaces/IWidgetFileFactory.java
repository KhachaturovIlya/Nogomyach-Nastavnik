package presenter.impl.interfaces;

import presenter.impl.CommandLibrary;
import presenter.impl.Widget;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IWidgetFileFactory {
    Map<Integer, Widget> constructButtons(Path configPath, CommandLibrary commandLibrary) throws IOException;
    Map<Integer, Widget> constructLabels(Path configPath, CommandLibrary commandLibrary) throws IOException;
}
