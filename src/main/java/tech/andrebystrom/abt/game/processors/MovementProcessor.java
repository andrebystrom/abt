package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.game.Input;
import tech.andrebystrom.abt.game.tetras.Tetra;

import java.util.List;

public class MovementProcessor implements Processor
{
    private int timesSinceLastMove = 0;

    @Override
    public void process(GameUpdateContext context)
    {
        var field = context.getGameField();
        var input = context.getInput();
        context.getTetras()
            .stream()
            .filter(Tetra::isActive)
            .findFirst()
            .ifPresent(t -> move(t, context.getTetras(), field, input));
    }

    private void move(Tetra t, List<Tetra> tetras, GameField field, Input input)
    {
        var stoppedTetras = tetras.stream().filter(tetra -> !tetra.isActive()).toList();
        switch(input)
        {
            case RIGHT ->
            {
                t.moveRight();
                if(!field.isValidPos(t, stoppedTetras))
                {
                    t.moveLeft();
                }
            }
            case LEFT ->
            {
                t.moveLeft();
                if(!field.isValidPos(t, stoppedTetras))
                {
                    t.moveRight();
                }
            }
            case UP ->
            {
                t.rotate();
                if(!field.isValidPos(t, stoppedTetras))
                {
                    t.undoRotation();
                }
            }
            case DOWN ->
            {
                t.moveDown();
                if(!field.isValidPos(t, stoppedTetras))
                {
                    t.moveUp();
                }
            }
        }
        timesSinceLastMove++;
        if(timesSinceLastMove >= 30)
        {
            timesSinceLastMove = 0;
            t.moveDown();
            if(!field.isValidPos(t, stoppedTetras))
            {
                t.moveUp();
            }
        }
    }
}