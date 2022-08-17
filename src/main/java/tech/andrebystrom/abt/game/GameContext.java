package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.tetras.Tetra;

import java.util.ArrayList;
import java.util.List;
public class GameContext
{
    private List<Tetra> tetras;
    private GameField gameField;
    private Input input;
    private ArrayList<Integer> completedLines;

    public GameContext(List<Tetra> tetras, GameField gameField, Input input)
    {
        this.tetras = tetras;
        this.gameField = gameField;
        this.input = input;
        completedLines = new ArrayList<>();
    }

    public List<Tetra> getTetras()
    {
        return tetras;
    }

    public GameField getGameField()
    {
        return gameField;
    }

    public Input getInput()
    {
        return input;
    }

    public ArrayList<Integer> getCompletedLines()
    {
        return completedLines;
    }

    public void setTetras(List<Tetra> tetras)
    {
        this.tetras = tetras;
    }

    public void setGameField(GameField gameField)
    {
        this.gameField = gameField;
    }

    public void setInput(Input input)
    {
        this.input = input;
    }

    public void setCompletedLines(ArrayList<Integer> completedLines)
    {
        this.completedLines = completedLines;
    }
}
