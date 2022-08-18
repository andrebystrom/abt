package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;

public class LostProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        context.getTetras()
            .stream()
            .filter(t -> t.isActive())
            .findFirst()
            .ifPresent(t ->
            {
                if(context.getGameField().isLost(t, context.getTetras()))
                {
                    context.setLost(true);
                }
            });
    }
}
