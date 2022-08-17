package tech.andrebystrom.abt.game;

import com.sun.tools.javac.Main;
import tech.andrebystrom.abt.game.processors.*;
import tech.andrebystrom.abt.game.tetras.Tetra;
import tech.andrebystrom.abt.game.tetras.TetraFactory;
import tech.andrebystrom.abt.views.MainWindow;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Game
{
    private int points;
    private int completedLinesCount;
    private List<Tetra> tetras;
    private GameField gameField;
    private final List<Processor> processors;

    private final TetraFactory tetraFactory = new TetraFactory();

    private final MainWindow window;

    // Synchronization.
    private boolean running;
    private final Lock gameLoopLock = new ReentrantLock();

    private final ConcurrentLinkedQueue<Input> inputQueue = new ConcurrentLinkedQueue<>();

    public Game(MainWindow window)
    {
        processors = List.of(
            new MovementProcessor(),
            new StoppedProcessor(),
            new LineProcessor(),
            new StaleTetraProcessor());
        this.window = window;
    }

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

    public void submitInput(Input input)
    {
        inputQueue.add(input);
    }

    private void gameLoop()
    {
        gameLoopLock.lock();
        try
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
                // We want 60 fps.
                int msPerFrame = 1000/30;
                var startTime = System.currentTimeMillis();
                // Do actual work.
                runIteration();
                var endTime = System.currentTimeMillis();
                try
                {
                    Thread.sleep(msPerFrame - (endTime - startTime));
                }
                catch(InterruptedException e)
                {
                    // ignore.
                }
            }
        }
        finally
        {
            gameLoopLock.unlock();
        }
    }

    private void runIteration()
    {
        var input = inputQueue.poll();
        inputQueue.clear();
        var context = new GameUpdateContext(tetras, gameField, input == null ? Input.NONE : input);
        for(var processor : processors)
        {
            processor.process(context);
        }
        completedLinesCount += context.getCompletedLines().size();
        points += completedLinesCount * 100;
        var state = new GameState(tetras, context.getCompletedLines(), points, completedLinesCount);
        try
        {
            SwingUtilities.invokeAndWait(() -> window.render(state));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}
