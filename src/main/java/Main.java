import presenter.IPresenter;
import presenter.impl.DefaultPresenter;
import presenter.impl.JsonWidgetFactory;
import presenter.impl.interfaces.IWidgetFileFactory;
import presenter.impl.widget.Widget;
import view.impl.DefaultView;

import java.net.URL;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        URL configsURL = Main.class.getClassLoader().getResource("configs");

        if (configsURL == null) {
            throw new RuntimeException("Configs URL not found!");
        }

        Path path = Path.of(configsURL.toURI()).getParent();

        IWidgetFileFactory widgetFactory = new JsonWidgetFactory(path);

        Map<Integer, Widget> widgets = widgetFactory.construct(
            Path.of("configs/scenes/footballField/widgets/labels")
        );

        IPresenter presenter = new DefaultPresenter(
            new DefaultView(),
            widgets);

        long lastTime = System.nanoTime();

        double deltaTime = 0;

        while (presenter.run(deltaTime)) {

            long currentTime = System.nanoTime();

            deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

            lastTime = currentTime;
        }
    }
}