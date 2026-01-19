import presenter.IPresenter;
import presenter.impl.DefaultPresenter;
import presenter.impl.JsonWidgetFactory;
import presenter.impl.Widget;
import presenter.impl.interfaces.IWidgetFileFactory;
import view.impl.DefaultView;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        URL configsURL = Main.class.getClassLoader().getResource("configs");

        if (configsURL == null) {
            throw new RuntimeException("Configs URL not found!");
        }

        Path path = Path.of(configsURL.toURI()).getParent();

        IWidgetFileFactory widgetFactory = new JsonWidgetFactory(path);

        List<Widget> labels = new ArrayList<>();

        labels = widgetFactory.constructLabels(
            Path.of("configs/scenes/footballField/widgets/labels.json")
        );

        IPresenter presenter = new DefaultPresenter(
            new DefaultView(),
            new ArrayList<>(),
            labels);

        long lastTime = System.nanoTime();

        double deltaTime = 0;

        while (presenter.run(deltaTime)) {

            long currentTime = System.nanoTime();

            deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

            lastTime = currentTime;
        }
    }
}