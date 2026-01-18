import presenter.IPresenter;
import presenter.impl.DefaultPresenter;
import view.impl.DefaultView;

public static void main(String[] args) {
    IPresenter presenter = new DefaultPresenter(new DefaultView(), new ArrayList<>(), new ArrayList<>());

    long lastTime = System.nanoTime();

    double deltaTime = 0;

    while(presenter.run(deltaTime)) {

        long currentTime = System.nanoTime();

        deltaTime = (currentTime - lastTime) / 1_000_000_000.0;

        lastTime = currentTime;
    }
}