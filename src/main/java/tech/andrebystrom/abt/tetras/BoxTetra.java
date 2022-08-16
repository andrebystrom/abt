package tech.andrebystrom.abt.tetras;

import tech.andrebystrom.abt.GameField;
import tech.andrebystrom.abt.Position;

import java.util.ArrayList;

public class BoxTetra extends Tetra
{
    public BoxTetra()
    {
        var positions = new ArrayList<Position>();
        int middle = GameField.WIDTH / 2;
        for(int i = middle; i < middle + 2; i++)
        {
            positions.add(new Position(i, GameField.HEIGHT));
            positions.add(new Position(i, GameField.HEIGHT + 1));
        }
        setPositions(positions);
    }

    /**
     * Rotates tetra, for the box this does nothing.
     */
    @Override
    public void rotate()
    {

    }

    /**
     * Undoes the rotation, for the box this does nothing.
     */
    @Override
    public void undoRotation()
    {

    }
}
