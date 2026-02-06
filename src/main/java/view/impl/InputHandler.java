package view.impl;

import shared.Vector2;
import view.Action;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    private final Queue<Action> actions;
    private final Queue<Vector2> mouseClickBuffer;
    private final JPanel canvas;
    private Vector2 mousePos = new Vector2(0, 0);

    public InputHandler(Queue<Action> actions, Queue<Vector2> mouseClickBuffer, JPanel canvas) {
        this.actions = actions;
        this.mouseClickBuffer = mouseClickBuffer;
        this.canvas = canvas;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("DEBUG: Key Pressed! Code: " + e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            actions.add(new Action.Quit());
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        double x = e.getX();
        double y = e.getY();

        mouseClickBuffer.add(new Vector2(x, y));
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mousePos = new Vector2(e.getX(), e.getY());
    }

    public Vector2 getMousePos() {
        return mousePos;
    }
}
