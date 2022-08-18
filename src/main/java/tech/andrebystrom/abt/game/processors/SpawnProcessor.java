package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.game.tetras.Tetra;

public class SpawnProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        var factory = context.getTetraFactory();
        if(context.getTetras().stream().noneMatch(Tetra::isActive))
        {
            context.getTetras().add(factory.pop());
        }
    }
}
