package view.impl;

import shared.ActionWidgetDTO;
import shared.UserInterfaceDTO;
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

    private void reDraw(Graphics2D graphics) {
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
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
