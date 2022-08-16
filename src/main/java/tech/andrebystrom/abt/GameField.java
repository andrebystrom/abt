package tech.andrebystrom.abt;

import tech.andrebystrom.abt.tetras.Tetra;

import java.util.*;
import java.util.stream.Collectors;

public class GameField
{
    private final HashSet<Position> positions = new HashSet<>();
    private final List<Tetra> stoppedTetras = new ArrayList<>();

    private Tetra activeTetra;
    public static final int HEIGHT = 20;
    public static final int WIDTH = 10;


    /**
     * Constructs a new GameField.
     */
    public GameField()
    {
        for(int height = 0; height < HEIGHT; height++)
        {
            for(int width = 0; width < WIDTH; width++)
            {
                positions.add(new Position(width, height));
            }
        }
    }

    public boolean hasActiveTetra()
    {
        return activeTetra != null;
    }

    public Optional<Tetra> getActiveTetra()
    {
        return Optional.ofNullable(activeTetra);
    }

    public void setActiveTetra(Tetra t)
    {
        activeTetra = t;
    }

    public boolean isValidPosition(Tetra t)
    {
        if(!positions.containsAll(t.getPositions()))
        {
            return false;
        }
        var stoppedPositions = getStoppedPositions();
        for(var position : t.getPositions())
        {
            if(stoppedPositions.contains(position))
            {
                return false;
            }
        }
        return true;
    }

    public void update()
    {
        //TODO:
        // Check and remove the completed lines. Shift downward
        // Check if game lost
        // Check if tetra has reached stop pos, if so set it as stoppedTetra and remove the active tetra.
    }

    private Set<Position> getStoppedPositions()
    {
        return stoppedTetras.stream()
            .flatMap(t -> t.getPositions().stream())
            .collect(Collectors.toSet());
    }

    private boolean isStopPosition(Tetra t)
    {
        var stoppedPositions = getStoppedPositions();
        for(var position : t.getPositions())
        {
            for(var stoppedPosition : stoppedPositions)
            {
                if(position.y() - 1 == stoppedPosition.y() && position.x() == stoppedPosition.x())
                {
                    return true;
                }
                if(position.y() == 0)
                {
                    return true;
                }
            }
        }
        return false;
    }
}
