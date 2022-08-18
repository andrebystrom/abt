package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;

public class LineTetra extends Tetra
{
    public LineTetra()
    {
        var middle = GameField.WIDTH / 2;
        var positions = new ArrayList<Position>();
        for(int i = middle - 1; i < middle - 1 + 4; i++)
        {
            positions.add(new Position(i, 0));
        }
        setPositions(positions);
    }

    @Override
    public void undoRotation()
    {
        rotate();
    }
}
