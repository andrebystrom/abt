package tech.andrebystrom.abt.game.processors;

import tech.andrebystrom.abt.game.GameUpdateContext;

public interface Processor
{
    void process(GameUpdateContext context);
}
