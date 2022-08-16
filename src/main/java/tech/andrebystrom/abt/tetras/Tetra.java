package tech.andrebystrom.abt.tetras;

import tech.andrebystrom.abt.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Tetra
{
    private final List<Position> positions = new ArrayList<>();

    /**
     * Get the positions of this tetra. Each position represents the upper left corner for each of the four blocks that
     * make up the tetra.
     *
     * @return the positions.
     */
    public Collection<Position> getPositions()
    {
        return List.copyOf(positions);
    }

    /**
     * Set the positions of the tetra. This has to be four connected positions.
     *
     * @param positions the positions.
     */
    public void setPositions(Collection<? extends Position> positions)
    {
        if(positions.size() != 4)
        {
            throw new IllegalArgumentException("Tetra has to have four positions");
        }
        for(var position : positions)
        {
            boolean found = false;
            for(var neighbor : positions)
            {
                if(position.equals(neighbor))
                {
                    continue;
                }
                if((Math.abs(position.x() - neighbor.x()) == 1 || position.x() - neighbor.x() == 0)
                    && (Math.abs(position.y() - neighbor.y()) == 1) || position.y() - neighbor.y() == 0)
                {
                    // Found neighbour.
                    found = true;
                    break;
                }
            }
            if(!found)
            {
                throw new IllegalArgumentException("Tetra has to be connected");
            }
        }
        this.positions.clear();
        this.positions.addAll(positions);
    }

    /**
     * Moves the tetra left.
     */
    public void moveLeft()
    {
        var newPositions = positions.stream()
            .map(p -> new Position(p.x() - 1, p.y()))
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Moves the tetra right
     */
    public void moveRight()
    {
        var newPositions = positions.stream()
            .map(p -> new Position(p.x() + 1, p.y()))
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Moves the tetra down.
     */
    public void moveDown()
    {
        var newPositions = positions.stream()
            .map(p -> new Position(p.x(), p.y() - 1))
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Moves the tetra up.
     */
    public void moveUp()
    {
        var newPositions = positions.stream()
            .map(p -> new Position(p.x(), p.y() + 1))
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Rotates the tetra.
     */
    public abstract void rotate();

    /**
     * Undoes the tetra rotation.
     */
    public abstract void undoRotation();
}
