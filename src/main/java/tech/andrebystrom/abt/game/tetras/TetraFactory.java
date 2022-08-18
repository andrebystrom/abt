package tech.andrebystrom.abt.game.tetras;

import java.util.List;
import java.util.Random;

/**
 * Constructs Tetras. Requires public no-arg constructor for the Tetras.
 */
public class TetraFactory
{
    private Class<? extends Tetra> peeked;
    private final Random random = new Random();
    private final List<Class<? extends Tetra>> tetras;

    public TetraFactory()
    {
        tetras = List.of(BoxTetra.class);
    }

    public Tetra pop()
    {
        if(peeked != null)
        {
            var tmp = peeked;
            peeked = null;
            return getInstance(tmp);
        }
        return getInstance(tetras.get(random.nextInt(tetras.size())));
    }

    public Class<? extends Tetra> peek()
    {
        peeked = tetras.get(random.nextInt(tetras.size()));
        return peeked;
    }

    private Tetra getInstance(Class<? extends Tetra> tetraClass)
    {
        try
        {
            var tetra = tetraClass.getConstructor().newInstance();
            tetra.setActive(true);
            return tetra;
        }
        catch(Exception e)
        {
            throw new RuntimeException("Failed to construct Tetra: " + e.getMessage(), e);
        }
    }
}
