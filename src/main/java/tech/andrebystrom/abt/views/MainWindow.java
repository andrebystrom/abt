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
        container.add(new LostView());
        revalidate();
        repaint();
    }
}
