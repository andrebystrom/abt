package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.processors.*;
import tech.andrebystrom.abt.game.tetras.Tetra;
import tech.andrebystrom.abt.game.tetras.TetraFactory;
import tech.andrebystrom.abt.shared.GameState;
import tech.andrebystrom.abt.shared.State;
import tech.andrebystrom.abt.views.MainWindow;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Game
{
    private State state = State.NOT_STARTED;
    private boolean restartRequested;

    private boolean pauseRequested;
    private boolean unpauseRequested;
    private int points;
    private int completedLinesCount;
    private List<Tetra> tetras;
    private GameField gameField;
    private List<Processor> processors;

    private final TetraFactory tetraFactory = new TetraFactory();

    private final MainWindow window;

    private ConcurrentLinkedQueue<Input> inputQueue;

    public Game(MainWindow window)
    {
        resetFields();
        this.window = window;
        this.window.addKeyListener(new KeyListener());
    }

    public synchronized void start()
    {
        if(state != State.NOT_STARTED)
        {
            throw new RuntimeException("Attempt to start multiple game loops");
        }
        state = State.RUNNING;
        Thread t = new Thread(this::gameLoop);
        t.start();
    }

    public void submitInput(Input input)
    {
        inputQueue.add(input);
    }

    private void gameLoop()
    {
        while(true)
        {
            // We want 30 fps.
            int msPerFrame = 1000 / 30;
            var startTime = System.currentTimeMillis();

            State currState;
            synchronized(this)
            {
                currState = state;
            }
            switch(currState)
            {
                case RUNNING -> runIteration();
                case LOST ->
                {
                    synchronized(this)
                    {
                        if(restartRequested)
                        {
                            restartRequested = false;
                            resetFields();
                            state = State.RUNNING;
                        }
                    }
                }
                case PAUSED ->
                {
                    synchronized(this)
                    {
                        if(unpauseRequested)
                        {
                            unpauseRequested = false;
                            state = State.RUNNING;
                        }
                    }
                }
            }

            var endTime = System.currentTimeMillis();
            try
            {
                var sleepTime = msPerFrame - (endTime - startTime);
                if(sleepTime >= 0)
                {
                    Thread.sleep(sleepTime);
                }
            }
            catch(InterruptedException e)
            {
                // ignore.
            }
        }
    }

    private void runIteration()
    {
        var input = inputQueue.poll();
        inputQueue.clear();
        var context = new GameUpdateContext(tetras, gameField, input == null ? Input.NONE : input, tetraFactory);
        for(var processor : processors)
        {
            processor.process(context);
        }
        var completedLinesInIteration = context.getCompletedLines().size();
        if(completedLinesInIteration > 0)
        {
            completedLinesCount += completedLinesInIteration;
            points += completedLinesInIteration * 100;
        }
        synchronized(this)
        {
            if(pauseRequested)
            {
                pauseRequested = false;
                state = State.PAUSED;
            }
        }
        if(context.isLost())
        {
            synchronized(this)
            {
                state = State.LOST;
            }
        }
        var state = new GameState(tetras, context.getCompletedLines(), points, completedLinesCount);
        try
        {
            if(context.isLost())
            {
                SwingUtilities.invokeAndWait(() -> window.renderLost(state));
            }
            else
            {
                SwingUtilities.invokeAndWait(() -> window.render(state));
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    private void resetFields()
    {
        processors = List.of(
            new SpawnProcessor(),
            new StoppedProcessor(),
            new MovementProcessor(),
            new LineProcessor(),
            new StaleTetraProcessor(),
            new LostProcessor());
        tetras = new ArrayList<>();
        gameField = new GameField();
        inputQueue = new ConcurrentLinkedQueue<>();
        points = 0;
        completedLinesCount = 0;
    }

    private class KeyListener implements java.awt.event.KeyListener
    {
        @Override
        public void keyTyped(KeyEvent e)
        {

        }

        @Override
        public void keyPressed(KeyEvent e)
        {
            State currState;
            synchronized(Game.this)
            {
                currState = state;
            }
            switch(currState)
            {
                case NOT_STARTED -> start();
                case LOST ->
                {
                    if(e.getKeyCode() == KeyEvent.VK_SPACE)
                    {
                        synchronized(Game.this)
                        {
                            restartRequested = true;
                        }
                    }
                }
                case RUNNING ->
                {
                    switch(e.getKeyCode())
                    {
                        case KeyEvent.VK_UP -> inputQueue.add(Input.UP);
                        case KeyEvent.VK_DOWN -> inputQueue.add(Input.DOWN);
                        case KeyEvent.VK_LEFT -> inputQueue.add(Input.LEFT);
                        case KeyEvent.VK_RIGHT -> inputQueue.add(Input.RIGHT);
                        case KeyEvent.VK_P ->
                        {
                            synchronized(Game.this)
                            {
                                pauseRequested = true;
                            }
                        }
                    }
                }
                case PAUSED ->
                {
                    if(e.getKeyCode() == KeyEvent.VK_P)
                    {
                        synchronized(Game.this)
                        {
                            unpauseRequested = true;
                        }
                    }
                }
            }
        }

        @Override
        public void keyReleased(KeyEvent e)
        {

        }
    }
}
