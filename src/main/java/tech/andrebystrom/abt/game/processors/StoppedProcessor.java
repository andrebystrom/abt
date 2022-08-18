package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameField;
import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.tetras.Tetra;
import tech.andrebystrom.abt.shared.Position;

import java.util.stream.Collectors;

public class StoppedProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        context.getTetras()
            .stream()
            .filter(Tetra::isActive)
            .findFirst()
            .ifPresent(t ->
            {
                var stoppedPositions = context.getTetras()
                    .stream()
                    .filter(st -> !st.isActive())
                    .flatMap(st -> st.getPositions().stream())
                    .collect(Collectors.toSet());
                for(var pos : t.getPositions())
                {
                    if(!stoppedPositions.contains(new Position(pos.x(), pos.y() + 1))
                    && pos.y() < GameField.HEIGHT - 1)
                    {
                        continue;
                    }
                    t.setActive(false);
                    return;
                }
            });
    }
}
