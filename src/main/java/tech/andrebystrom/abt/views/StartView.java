package tech.andrebystrom.abt.views;

import javax.swing.*;
import java.awt.*;

public class StartView extends JPanel
{
    public StartView()
    {
        setPreferredSize(new Dimension(400, 800));
        add(new JLabel("Press any key to start.."));
    }
}
