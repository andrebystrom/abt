package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.tetras.Tetra;

public class StoppedProcessor implements Processor
{
    private int timesSinceStopped = 0;
    @Override
    public void process(GameUpdateContext context)
    {
        context.getTetras()
            .stream()
            .filter(Tetra::isActive)
            .findFirst()
            .ifPresent(t ->
            {
                if(context.getGameField().isStopPos(t, context.getTetras())
                && timesSinceStopped++ >= 5)
                {
                    timesSinceStopped = 0;
                    t.setActive(false);
                }
            });
    }
}
