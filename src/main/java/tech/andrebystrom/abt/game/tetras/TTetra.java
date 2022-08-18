package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;
import java.util.Collection;

public class TTetra extends Tetra
{
    private Collection<Position> prevPositions;

    public TTetra()
    {
        var positions = new ArrayList<Position>();
        var middle = GameField.WIDTH / 2;
        positions.add(new Position(middle, 0));
        positions.add(new Position(middle - 1, 1));
        positions.add(new Position(middle, 1));
        positions.add(new Position(middle + 1, 1));
        setPositions(positions);
    }

    @Override
    public void rotate()
    {
        prevPositions = getPositions();
        super.rotate();
    }

    @Override
    public void undoRotation()
    {
        if(prevPositions != null)
        {
            setPositions(prevPositions);
        }
    }
}
