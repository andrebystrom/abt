package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;
import tech.andrebystrom.abt.shared.Position;

import java.util.HashSet;

public class LostProcessor implements Processor
{
    @Override
    public void process(GameUpdateContext context)
    {
        var tmp = new HashSet<Position>();
        context.getTetras()
            .stream()
            .flatMap(t -> t.getPositions().stream())
            .filter(p -> !tmp.add(p))
            .findFirst()
            .ifPresent(p -> context.setLost(true));
    }
}
