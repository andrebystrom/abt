package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.tetras.Tetra;

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
                if(context.getGameField().isStopPos(t, context.getTetras()))
                {
                    t.setActive(false);
                }
            });
    }
}
