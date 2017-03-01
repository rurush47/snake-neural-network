package generic;

import java.util.ArrayList;

public abstract class Individual
{
    public double fitness;

    public abstract void Initialize();
    public abstract ArrayList<Object> getGenome();
    public abstract void applyGenome(ArrayList<Object> genome);
    public abstract Object mutateGene(Object gene);
    public abstract Individual createNew();
}
