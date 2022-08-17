package tech.andrebystrom.abt.game;

import tech.andrebystrom.abt.game.tetras.Tetra;

import java.util.List;

public record GameState(
    List<Tetra> tetras,
    List<Integer> completedLines,
    int points,
    int completedLinesCount)
{
}
