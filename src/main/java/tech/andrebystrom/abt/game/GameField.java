package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.tetras.Tetra;
import tech.andrebystrom.abt.shared.Position;

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

    /**
     * Checks if the tetra is on a stop position.
     *
     * @param t      the tetra to check.
     * @param tetras the list of all tetras.
     * @return true if on stop pos, false otherwise.
     */
    public boolean isStopPos(Tetra t, List<Tetra> tetras)
    {
        var stoppedPositions = tetras.stream()
            .filter(tetra -> !tetra.isActive())
            .flatMap(tetra -> tetra.getPositions().stream())
            .collect(Collectors.toSet());
        for(var pos : t.getPositions())
        {
            if(stoppedPositions.contains(new Position(pos.x(), pos.y() + 1)))
            {
                return true;
            }
            if(pos.y() >= HEIGHT - 1)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the game is lost.
     *
     * @param activeTetra the active tetra.
     * @param tetras      all the tetras.
     * @return true if lost, false otherwise.
     */
    public boolean isLost(Tetra activeTetra, List<Tetra> tetras)
    {
        return activeTetra.getPositions()
            .stream()
            .anyMatch(p -> p.y() == 0)
            && isStopPos(activeTetra, tetras);
    }
}