package presenter.impl.interfaces;

import presenter.impl.CommandLibrary;
import presenter.impl.Widget;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface IWidgetFileFactory {
    public List<Widget> constructButtons(Path configPath, CommandLibrary commandLibrary) throws IOException;
    public List<Widget> constructLabels(Path configPath) throws IOException;
}
