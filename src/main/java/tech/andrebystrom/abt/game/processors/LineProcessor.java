package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.game.tetras.Tetra;
import tech.andrebystrom.abt.shared.Position;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class LineProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        var completedLines = new ArrayList<Integer>();
        var positions = context.getTetras()
            .stream()
            .filter(t -> !t.isActive())
            .flatMap(t -> t.getPositions().stream())
            .collect(Collectors.toSet());
        for(int i = 0; i < GameField.HEIGHT; i++)
        {
            boolean found = false;
            for(int j = 0; j < GameField.WIDTH; j++)
            {
                if(!positions.contains(new Position(j, i)))
                {
                    break;
                }
                if(j == GameField.WIDTH - 1)
                {
                    found = true;
                }
            }
            if(!found)
            {
                continue;
            }
            completedLines.add(i);
        }

        completedLines.sort(Comparator.comparingInt(i -> i));
        for(var completedLine : completedLines)
        {
            context.getTetras()
                .stream()
                .filter(t -> !t.isActive())
                .forEach(t -> t.removePositionsAtY(completedLine));

            for(int i = 0; i < completedLine; i++)
            {
                final int y = i;
                context.getTetras()
                    .stream()
                    .filter(t ->
                    {
                        return t.getPositions()
                            .stream()
                            .anyMatch(p -> p.y() == y);
                    })
                    .forEach(Tetra::moveDown);
            }
        }

        context.setCompletedLines(completedLines);
    }
}
