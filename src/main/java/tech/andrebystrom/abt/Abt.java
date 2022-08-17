package tech.andrebystrom.abt;

import tech.andrebystrom.abt.views.MainWindow;

import javax.swing.*;

public class Abt
{
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(() -> new MainWindow().setVisible(true));
    }
}
