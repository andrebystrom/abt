package tech.andrebystrom.abt;

import tech.andrebystrom.abt.tetras.Tetra;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameField
{
    private final HashSet<Position> positions = new HashSet<>();
    private final List<Tetra> tetras = new ArrayList<>(); // Tetras currently on the field.
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
        var currentTetraPositions = tetras.stream()
            .flatMap(p -> p.getPositions().stream())
            .collect(Collectors.toSet());
        for(var position : t.getPositions())
        {
            if(currentTetraPositions.contains(position))
            {
                return false;
            }
        }
        return true;
    }

    public PlayingFieldUpdateResult update()
    {
        // Go through the matrix and check if any rows are completed

        // Remove the lines that are completed and shift down.

        // Check if we have lost, that is if the active tetra is standing on a tetra with a y-pos of HEIGHT - 1.

        // Check if the active tetra has "landed", add it to the tetra list and set active tetra to null if that's
        // the case.
        return null;
    }
}
