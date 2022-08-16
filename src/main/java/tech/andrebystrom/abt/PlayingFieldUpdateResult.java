package tech.andrebystrom.abt;

import tech.andrebystrom.abt.tetras.Tetra;

import java.util.List;

public record PlayingFieldUpdateResult(List<Tetra> tetras, List<Integer> linesCompleted, boolean lost)
{
}
