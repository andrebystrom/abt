package tech.andrebystrom.abt;

public class Game
{
    private GameField gameField;
    private volatile boolean running = false;

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
        while(running)
        {

        }
    }
}
