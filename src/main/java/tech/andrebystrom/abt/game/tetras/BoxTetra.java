package tech.andrebystrom.abt.game.tetras;

import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;

public class BoxTetra extends Tetra
{
    public BoxTetra()
    {
        var positions = new ArrayList<Position>();
        int middle = GameField.WIDTH / 2;
        for(int i = middle; i < middle + 2; i++)
        {
            positions.add(new Position(i, 0));
            positions.add(new Position(i, 1));
        }
        setPositions(positions);
    }
}
