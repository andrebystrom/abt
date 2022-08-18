package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.game.GameState;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    private GameFieldView gameFieldView;
    private StartView startView;

    private JPanel container;

    public MainWindow()
    {
        super("ABT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 1000));
        this.setResizable(false);
        setLayout(new GridBagLayout());

        gameFieldView = new GameFieldView();
        startView = new StartView();
        container = new JPanel();
        container.setPreferredSize(new Dimension(400, 800));
        container.add(startView);
        add(container);
    }

    public void render(GameState state)
    {
        container.removeAll();
        container.add(gameFieldView);
        revalidate();
        repaint();
        gameFieldView.setState(state);
        gameFieldView.repaint();
    }
}
