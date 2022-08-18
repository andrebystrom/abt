package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.game.GameState;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame
{
    private GameFieldView gameFieldView;

    public MainWindow()
    {
        super("ABT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(300, 500));
        this.setResizable(false);
        setLayout(new GridBagLayout());

        gameFieldView = new GameFieldView();
        add(gameFieldView);
    }

    public void render(GameState state)
    {
        gameFieldView.setState(state);

        gameFieldView.repaint();
    }
}
