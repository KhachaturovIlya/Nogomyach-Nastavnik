package view.impl;

import view.Action;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class InputHandler implements KeyListener, MouseListener {
    private final Queue<Action> actions;
    private final JPanel canvas;

    public InputHandler(Queue<Action> actions, JPanel canvas) {
        this.actions = actions;
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

        System.out.println("DEBUG: Actions size: " + actions.size());
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}
