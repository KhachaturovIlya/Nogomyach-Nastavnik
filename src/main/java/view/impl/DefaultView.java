package view.impl;

import shared.*;
import shared.Shape;
import view.Action;
import view.IView;

import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class DefaultView implements IView {
    private final JFrame frame;
    private final JPanel canvas;

    private UserInterfaceDTO userInterfaceDTO;
    private WorldDTO worldDTO;

    private final Queue<Action> actionBuffer = new java.util.concurrent.ConcurrentLinkedQueue<>();
    private final Queue<Vector2> mouseClickBuffer = new java.util.concurrent.ConcurrentLinkedQueue<>();

    InputHandler inputHandler;

    private final List<List<Integer>> interactedWidgets = new ArrayList<>();

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

            if (widgetDTO.text() != null) {
                if (widgetDTO.button() && widgetDTO.shape().contains(mousePos.x - x / w, mousePos.y - y / h)) {
                    graphics.setColor((new Color(widgetDTO.textColor().getFullColor(), true)).darker());
                } else {
                    graphics.setColor(new Color(widgetDTO.textColor().getFullColor(), true));
                }

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

        inputHandler = new InputHandler(this.actionBuffer, this.mouseClickBuffer, this.canvas);

        frame.addKeyListener(inputHandler);
        canvas.addMouseListener(inputHandler);
        canvas.addMouseMotionListener(inputHandler);

        frame.setFocusable(true);
        frame.requestFocus();
    }

    public DefaultView() {
        this.frame = new JFrame("Nogamach-Nastavnik");
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
        interactedWidgets.clear();

        Action action;
        while ((action = actionBuffer.poll()) != null) {
            result.add(action);
        }

        Vector2 mouseClick;
        while ((mouseClick = mouseClickBuffer.poll()) != null) {
            for (int i = actionWidgetDTOs.size() - 1; i >= 0; i--) {
                if (isPointInsideDTO(mouseClick.x, mouseClick.y, actionWidgetDTOs.get(i))) {
                    interactedWidgets.add(actionWidgetDTOs.get(i).ids());
                    break;
                }
            }
        }

        return result;
    }

    @Override
    public List<List<Integer>> getInteractedWidgets() {
        return interactedWidgets;
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
        frame.dispose();
    }
}
