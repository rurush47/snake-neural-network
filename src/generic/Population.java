package generic;

import java.util.ArrayList;
import java.util.Collections;

public class Population<T> {

    private Class<T> type;
    private ArrayList<Individual> individuals = new ArrayList<>();
    private int populationSize;

    public Population(int size, boolean init, Class<T> clazz)
            throws InstantiationException, IllegalAccessException
    {
        this.type = clazz;
        populationSize = size;

        if (init)
        {
            Init();
        }
    }

    private void Init() throws IllegalAccessException, InstantiationException {
        for (int i = 0; i < populationSize; i++)
        {
            Individual ind = (Individual) type.newInstance();
            individuals.add(ind);
        }
    }


    public int getSize()
    {
        return individuals.size();
    }

    void addIndividual(Individual ind)
    {
        individuals.add(ind);
    }

    public Individual getFittest()
    {
        if(!individuals.isEmpty())
        {
            Individual fittest = individuals.get(0);

            for (Individual ind : individuals) {
                if (ind.fitness >= fittest.fitness) {
                    fittest = ind;
                }
            }

            return fittest;
        }
        else
        {
            return null;
        }
    }

    public Individual getIndividualAt(int index)
    {
        return individuals.get(index);
    }

    public double getAverageFitness()
    {
        int sum = getTotalFitness();
        return sum/ individuals.size();
    }

    Individual getIndividualFromRoulette()
    {
        int sum = getTotalFitness();
        int rouletteSpin = (int) (Math.random() * sum);
        int currentSum = 0;

        for (Individual currentIndividual : individuals) {
            currentSum += currentIndividual.fitness;

            if (currentSum >= rouletteSpin) {
                return currentIndividual;
            }
        }

        return null;
    }

    private int getTotalFitness()
    {
        int sum = 0;
        for (Individual individual : individuals) {
            sum += individual.fitness;
        }

        return sum;
    }

    public void sortByFitness()
    {
        Collections.sort(individuals, (p1, p2) -> Double.valueOf(p2.fitness).compareTo(p1.fitness));
    }

    public Class<T> getGenericType()
    {
        return type;
    }
}
