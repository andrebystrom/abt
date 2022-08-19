package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;

public class TTetra extends Tetra
{
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
}
