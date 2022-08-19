package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.shared.GameState;

import javax.swing.*;
import java.awt.*;

public class LostView extends JPanel
{
    private JLabel points;
    private JLabel completedLines;
    public LostView()
    {
        setPreferredSize(new Dimension(400, 800));
        setBackground(Color.RED);
        setLayout(new GridBagLayout());
        var constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy = 0;
        constraints.gridx = 1;
        add(new JLabel("YOU LOST!"), constraints);

        constraints.gridy = 1;
        add(new JLabel("Press any key to restart"), constraints);

        constraints.gridy = 3;
        add(new JLabel("SUMMARY:"), constraints);
        constraints.gridy = 4;
        points = new JLabel("Points: 0");
        add(points, constraints);

        constraints.gridy = 5;
        completedLines = new JLabel("Lines: 0");
        add(completedLines, constraints);
    }

    public void setGameState(GameState state)
    {
        points.setText("Points: " + state.points());
        completedLines.setText("Lines: " + state.completedLinesCount());
    }
}
