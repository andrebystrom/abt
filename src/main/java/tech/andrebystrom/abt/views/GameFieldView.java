package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.game.tetras.BoxTetra;
import tech.andrebystrom.abt.game.tetras.Tetra;

import javax.swing.*;
import java.awt.*;

public class GameFieldView extends JPanel
{
    public GameFieldView()
    {
        setPreferredSize(new Dimension(150, 300));
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 149, 299);
    }

    private Color getColor(Class<? extends Tetra> tetra)
    {
        if(tetra.equals(BoxTetra.class))
        {
            return Color.blue;
        }
        return Color.cyan;
    }
}
