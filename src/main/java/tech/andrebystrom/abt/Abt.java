package tech.andrebystrom.abt;

import tech.andrebystrom.abt.game.Game;
import tech.andrebystrom.abt.views.MainWindow;

import javax.swing.*;

public class Abt
{
    public static void main(String[] args)
    {
        var window = new MainWindow();
        var game = new Game(window);
        SwingUtilities.invokeLater(() -> window.setVisible(true));
        game.start();
    }
}
