package view.impl;

import shared.*;
import shared.Shape;
import view.Action;
import view.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DefaultView implements IView {
    private final JFrame frame;
    private final JPanel canvas;

    private final SoundPlayer soundPlayer;
    private final MusicPlayer musicPlayer;

    private UserInterfaceDTO userInterfaceDTO;
    private WorldDTO worldDTO;

    private final Queue<Action> actionBuffer = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private final Queue<Vector2> mouseClickBuffer = new java.util.concurrent.ConcurrentLinkedQueue<>();

    private InputHandler inputHandler;
    private final FontMapper fontMapper;

    private void DrawWorldDTO(Graphics2D graphics) {

    }

    private void DrawUIDTO(Graphics2D graphics) {
        if (userInterfaceDTO == null) return;

        if (userInterfaceDTO.widgetDTOS() == null || userInterfaceDTO.widgetDTOS().isEmpty()) return;

        Vector2 rawPos = inputHandler.getMousePos();

        for (VisualWidgetDTO widgetDTO : userInterfaceDTO.widgetDTOS()) {
            int x = (int) (widgetDTO.position().x * canvas.getWidth());
            int y = (int) (widgetDTO.position().y * canvas.getHeight());

            double w = canvas.getWidth();
            double h = canvas.getHeight();

            Vector2 mousePos = new Vector2(rawPos.x / w, rawPos.y / h);

            if (widgetDTO.button() && widgetDTO.shape().contains(mousePos.x - x / w, mousePos.y - y / h)) {
                graphics.setColor((new Color(widgetDTO.shapeColor().getFullColor(), true)).darker());
            } else {
                graphics.setColor(new Color(widgetDTO.shapeColor().getFullColor(), true));
            }

            Shape shape = widgetDTO.shape();
            switch (shape) {
                case Shape.Circle circle -> {
                    w *= circle.radius() * 2;
                    h *= circle.radius() * 2;
                    graphics.fillOval(x, y, (int) w, (int) h);
                }
                case Shape.Rectangle rectangle -> {
                    w *= rectangle.width();
                    h *= rectangle.height();
                    graphics.fillRect(x, y, (int) w, (int) h);
                }
                case Shape.Square square -> {
                    w *= square.side();
                    h *= square.side();
                    graphics.fillRect(x, y, (int) w, (int) h);
                }
            }

            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            graphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);

            if (widgetDTO.text() != null) {
                if (widgetDTO.button() && widgetDTO.shape().contains(mousePos.x - x / w, mousePos.y - y / h)) {
                    graphics.setColor((new Color(widgetDTO.textColor().getFullColor(), true)).darker());
                } else {
                    graphics.setColor(new Color(widgetDTO.textColor().getFullColor(), true));
                }

                Font font = fontMapper.getFont(widgetDTO.textType());
                if (font.canDisplayUpTo(widgetDTO.text()) == -1) {
                    graphics.setFont(font);
                }
                graphics.setFont(getOptimalFontSize(graphics, widgetDTO.text(), (int) w, (int) h));

                FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());

                int textX = (int) (x + (w - metrics.stringWidth(widgetDTO.text())) / 2);
                int textY = (int) (y + (h - (metrics.getAscent() + metrics.getDescent())) / 2 + metrics.getAscent());

                graphics.drawString(widgetDTO.text(), textX, textY);
            }
        }
    }

    private Font getOptimalFontSize(Graphics2D graphics, String text, int maxWidth, int maxHeight) {
        Font currentFont = graphics.getFont();
        int fontSize = maxHeight;

        while (fontSize > 5) {
            Font scaledFont = currentFont.deriveFont((float) fontSize);
            FontMetrics fm = graphics.getFontMetrics(scaledFont);

            if (fm.stringWidth(text) < maxWidth * 0.7 && fm.getHeight() < maxHeight * 0.7) {
                return scaledFont;
            }
            fontSize--;
        }
        return currentFont.deriveFont(5f);
    }

    private void reDraw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        DrawWorldDTO(graphics);

        DrawUIDTO(graphics);
    }

    private void initWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);

        frame.add(canvas);
        frame.setVisible(true);

        GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        if (device.isFullScreenSupported()) {
            device.setFullScreenWindow(frame);
        } else {
            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }

        inputHandler = new InputHandler(this.actionBuffer, this.mouseClickBuffer, this.canvas);

        frame.addKeyListener(inputHandler);
        canvas.addMouseListener(inputHandler);
        canvas.addMouseMotionListener(inputHandler);

        frame.setFocusable(true);
        frame.requestFocus();

        musicPlayer.play("Push-Long-Version");
    }

    public DefaultView(Path resourcesPath) {
        this.frame = new JFrame("Nogamach-Nastavnik");
        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                reDraw((Graphics2D) g);
            }
        };

        fontMapper = new FontMapper(resourcesPath.resolve("fonts"));
        soundPlayer = new SoundPlayer(resourcesPath.resolve("audio/effects"));
        musicPlayer = new MusicPlayer(resourcesPath.resolve("audio/soundtracks"));

        initWindow();
    }

    @Override
    public void setVolume(float volume) {
        musicPlayer.setVolume(volume);
        soundPlayer.setVolume(volume);
    }

    @Override
    public void renderWorld(WorldDTO worldDTO) {
        this.worldDTO = worldDTO;
    }

    @Override
    public void renderUI(UserInterfaceDTO userInterfaceDTO) {
        this.userInterfaceDTO = userInterfaceDTO;
    }

    @Override
    public List<Action> getActions(List<ActionWidgetDTO> actionWidgetDTOs) {
        List<Action> result = new ArrayList<>();

        Action action;
        while ((action = actionBuffer.poll()) != null) {
            result.add(action);
        }

        Vector2 mouseClick;
        while ((mouseClick = mouseClickBuffer.poll()) != null) {
            soundPlayer.play("click");
            for (int i = actionWidgetDTOs.size() - 1; i >= 0; i--) {
                if (isPointInsideDTO(mouseClick.x, mouseClick.y, actionWidgetDTOs.get(i))) {
                    result.add(new Action.WidgetClicked(actionWidgetDTOs.get(i).ids()));
                    break;
                }
            }
        }

        return result;
    }

    private boolean isPointInsideDTO(double x, double y, ActionWidgetDTO actionWidgetDTO) {
        double normX = x / canvas.getWidth();
        double normY = y / canvas.getHeight();

        return actionWidgetDTO.shape().contains(normX - actionWidgetDTO.position().x,
            normY - actionWidgetDTO.position().y);
    }

    @Override
    public void update() {
        canvas.repaint();
    }

    @Override
    public void close() {
        musicPlayer.stop();

        try {
            Thread.sleep(220);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        frame.dispose();
        soundPlayer.close();
    }
}
