package tech.andrebystrom.abt.views;

import javax.swing.*;
import java.awt.*;

public class LostView extends JPanel
{
    public LostView()
    {
        setPreferredSize(new Dimension(400, 800));
        add(new JLabel("YOU LOST!"));
    }
}
