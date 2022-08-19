package tech.andrebystrom.abt.views;

import tech.andrebystrom.abt.shared.GameState;
import tech.andrebystrom.abt.game.tetras.BoxTetra;
import tech.andrebystrom.abt.game.tetras.Tetra;

import javax.swing.*;
import java.awt.*;

public class GameFieldView extends JPanel
{
    private GameState state;

    public GameFieldView()
    {
        setPreferredSize(new Dimension(400, 800));
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        g.setColor(Color.black);
        g.fillRect(0, 0, 400, 800);

        if(state != null)
        {
            for(var tetra : state.tetras())
            {
                g.setColor(getColor(tetra.getClass()));
                for(var pos : tetra.getPositions())
                {
                    g.fillRect(40 * pos.x(), 40 * pos.y(), 40, 40);
                }
            }
        }
    }

    public void setState(GameState state)
    {
        this.state = state;
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
