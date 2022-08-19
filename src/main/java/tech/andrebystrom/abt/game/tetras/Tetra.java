package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Tetra
{
    private boolean isActive;
    private final List<Position> positions = new ArrayList<>();
    private List<Position> prevPositions;

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
    public final List<Position> getPositions()
    {
        return new ArrayList<>(positions);
    }

    /**
     * Set the positions of the tetra. This has to be at most four connected positions.
     *
     * @param positions the positions.
     */
    public final void setPositions(List<? extends Position> positions)
    {
        if(positions.size() > 4)
        {
            throw new IllegalArgumentException("Tetra cannot have more than four positions");
        }
        if(isActive)
        {
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
            .map(p -> new Position(p.x(), p.y() + 1))
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Moves the tetra up.
     */
    public void moveUp()
    {
        var newPositions = positions.stream()
            .map(p -> new Position(p.x(), p.y() - 1))
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
    public void rotate()
    {
        var positions = getPositions();
        if(positions.size() == 0)
        {
            return;
        }
        prevPositions = positions;

        var first = positions.get(0);
        var xShift = first.x();
        var yShift = first.y();
        var newPositions = getPositions().stream()
            .map(p ->
            {
                // Counter-clockwise rotation translated to the first position of the object.
                // i.e. multiplication by the matrix
                // [0 -1]
                // [1  0]
                // Where y is reflected along the x-axis first.
                // TODO: fix so it's possible to rotate more than 90 degrees.
                //  Right now it rotates back at the 2nd rotation.
                return new Position(xShift + (p.y() - yShift), yShift + (p.x() - xShift));
            })
            .collect(Collectors.toList());
        setPositions(newPositions);
    }

    /**
     * Undoes the tetra rotation.
     */
    public void undoRotation()
    {
        if(prevPositions != null)
        {
            setPositions(prevPositions);
        }
    }
}
