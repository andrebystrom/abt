package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Tetra
{
    private boolean isActive;
    private final List<Position> positions = new ArrayList<>();

    /**
     * Returns true if the tetra is active, false otherwise.
     *
     * @return true if the tetra is active, false otherwise.
     */
    public boolean isActive()
    {
        return isActive;
    }

    /**
     * Set whether the tetra is active or not.
     *
     * @param active true if active, false otherwise.
     */
    public void setActive(boolean active)
    {
        isActive = active;
    }


    /**
     * Checks if the tetra has any positions.
     *
     * @return true if it has any positions, false otherwise.
     */
    public final boolean hasAnyPositions()
    {
        return !positions.isEmpty();
    }

    /**
     * Get the positions of this tetra. Each position represents the upper left corner for each of the four blocks that
     * make up the tetra.
     *
     * @return the positions.
     */
    public final Collection<Position> getPositions()
    {
        return new ArrayList<>(positions);
    }

    /**
     * Set the positions of the tetra. This has to be at most four connected positions.
     *
     * @param positions the positions.
     */
    public final void setPositions(Collection<? extends Position> positions)
    {
        if(positions.size() > 4)
        {
            throw new IllegalArgumentException("Tetra cannot have more than four positions");
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
     * Removes all positions at the given Y coordinate.
     *
     * @param y the Y coordinate.
     */
    public void removePositionsAtY(int y)
    {
        positions.removeIf(p -> p.y() == y);
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
