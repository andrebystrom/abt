package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.tetras.Tetra;

import java.util.*;
import java.util.stream.Collectors;

public class GameField
{
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;

    /**
     * Checks if the tetra is within the field.
     *
     * @param t the tetra to check.
     * @return true if in field, false otherwise.
     */
    public boolean isWithinField(Tetra t)
    {
        for(var pos : t.getPositions())
        {
            if(pos.x() < 0 || pos.x() >= WIDTH || pos.y() < 0 || pos.y() >= HEIGHT)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks that the tetra has a valid position and is not on top of a stopped tetra.
     *
     * @param t the tetra to check.
     * @return true if valid position, false otherwise.
     */
    public boolean isValidPos(Tetra t, List<Tetra> stoppedTetras)
    {
        if(!isWithinField(t))
        {
            return false;
        }

        var stoppedPositions = stoppedTetras.stream()
            .flatMap(tetra -> tetra.getPositions().stream())
            .collect(Collectors.toSet());

        for(var pos : t.getPositions())
        {
            if(stoppedPositions.contains(pos))
            {
                return false;
            }
        }

        return true;
    }
}
