package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;

public class StaleTetraProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        context.getTetras()
            .removeIf(t -> !t.hasAnyPositions());
    }
}
