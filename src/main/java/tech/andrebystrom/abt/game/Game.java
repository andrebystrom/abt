package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.processors.Processor;
import tech.andrebystrom.abt.game.tetras.Tetra;

import java.util.List;

public class Game
{
    private List<Tetra> tetras;
    private GameField gameField;
    private List<Processor> processors;
    private boolean running;

    public synchronized void start()
    {
        if(running)
        {
            return;
        }

        running = true;
        Thread t = new Thread(this::gameLoop);
        t.start();
    }

    public synchronized void pause()
    {
        if(!running)
        {
            return;
        }
        running = false;
    }

    public synchronized void reset()
    {

    }

    private void gameLoop()
    {
        while(true)
        {
            // Check if time to exit.
            synchronized(this)
            {
                if(!running)
                {
                    break;
                }
            }
            var context = new GameContext(tetras, gameField, Input.NONE);
            for(var processor : processors)
            {
                processor.process(context);
            }

        }
    }
}
