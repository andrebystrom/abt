package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.shared.GameState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MainWindow extends JFrame
{
    private GameFieldView gameFieldView;
    private StartView startView;

    private JPanel container;

    private JLabel pointsLabel, linesLabel;

    public MainWindow()
    {
        super("ABT");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 1000));
        this.setResizable(false);
        setLayout(new GridBagLayout());

        var cs = new GridBagConstraints();
        cs.fill = GridBagConstraints.HORIZONTAL;
        cs.gridy = 0;
        cs.gridx = 0;
        pointsLabel = new JLabel("Points: 0");
        add(pointsLabel, cs);
        cs.gridy = 1;
        linesLabel = new JLabel("Lines: 0");
        add(linesLabel, cs);

        cs.gridy = 2;
        gameFieldView = new GameFieldView();
        startView = new StartView();
        container = new JPanel();
        container.setPreferredSize(new Dimension(400, 800));
        container.add(startView);
        add(container, cs);
        pack();
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent event)
            {
                if(event.isControlDown() && event.getKeyCode() == KeyEvent.VK_C)
                {
                    System.exit(0);
                }
            }
        });
    }

    public void render(GameState state)
    {
        pointsLabel.setText("Points: " + state.points());
        linesLabel.setText("Lines: " + state.completedLinesCount());
        container.removeAll();
        container.add(gameFieldView);
        revalidate();
        repaint();
        gameFieldView.setState(state);
        gameFieldView.repaint();
    }

    public void renderLost(GameState state)
    {
        container.removeAll();
        var view = new LostView();
        view.setGameState(state);
        container.add(view);
        revalidate();
        repaint();
    }
}
