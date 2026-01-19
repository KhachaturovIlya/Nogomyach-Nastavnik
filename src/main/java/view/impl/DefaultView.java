package view.impl;

import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
import shared.VisualWidgetDTO;
import shared.Shape;
import shared.WorldDTO;
import view.Action;
import view.IView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DefaultView implements IView {
    private final JFrame frame;
    private final JPanel canvas;

    private UserInterfaceDTO userInterfaceDTO;
    private WorldDTO worldDTO;

    private final Queue<Action> actionBuffer = new java.util.concurrent.ConcurrentLinkedQueue<>();

    private void DrawWorldDTO(Graphics2D graphics) {

    }

    private void DrawUIDTO(Graphics2D graphics) {
        if (userInterfaceDTO == null) return;

        if (userInterfaceDTO.widgetDTOS() == null || userInterfaceDTO.widgetDTOS().isEmpty()) return;

        for (VisualWidgetDTO widgetDTO : userInterfaceDTO.widgetDTOS()) {
            int x = (int) (widgetDTO.position().x * canvas.getWidth());
            int y = (int) (widgetDTO.position().y * canvas.getHeight());

            double w = canvas.getWidth();
            double h = canvas.getHeight();

            graphics.setColor(new Color(widgetDTO.shapeColor().getFullColor(), true));

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

            if (widgetDTO.text() != null) {
                graphics.setColor(new Color(widgetDTO.textColor().getFullColor(), true));

                FontMetrics metrics = graphics.getFontMetrics(graphics.getFont());

                int textX = (int) (x + (w - metrics.stringWidth(widgetDTO.text())) / 2);
                int textY = (int) (y + ((h - metrics.getHeight()) / 2) + metrics.getAscent());

                graphics.drawString(widgetDTO.text(), textX, textY);
            }
        }
    }

    private void reDraw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        DrawWorldDTO(graphics);

        DrawUIDTO(graphics);
    }

    private void initWindow() {
        frame.setUndecorated(true);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(canvas);
        frame.setVisible(true);

        InputHandler inputHandler = new InputHandler(this.actionBuffer, this.canvas);

        frame.addKeyListener(inputHandler);
        canvas.addMouseListener(inputHandler);

        frame.setFocusable(true);
        frame.requestFocus();
    }

    public DefaultView() {
        this.frame = new JFrame("Noga Mach Manager");
        this.canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                reDraw((Graphics2D) g);
            }
        };

        initWindow();
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

        return result;
    }

    @Override
    public void update() {
        canvas.repaint();
    }

    @Override
    public void close() {
        frame.dispose();
    }
}
