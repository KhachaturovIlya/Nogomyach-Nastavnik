package presenter.impl.interfaces;

import presenter.impl.CommandLibrary;
import presenter.impl.widget.Widget;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public interface IWidgetFileFactory {
    Map<Integer, Widget> construct(Path configPath) throws IOException;
}
