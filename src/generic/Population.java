package generic;

import snake.Snake;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Population {

    private ArrayList<Snake> snakes = new ArrayList<>();
    private int populationSize;

    public Population(int size, boolean init)
    {
        populationSize = size;

        if (init)
        {
            Init();
        }
    }

    private void Init()
    {
        for (int i = 0; i < populationSize; i++)
        {
            Snake ind = new Snake();
            snakes.add(ind);
        }
    }


    public int getSize()
    {
        return snakes.size();
    }

    void addIndividual(Snake ind)
    {
        snakes.add(ind);
    }

    public Snake getFittest()
    {
        if(!snakes.isEmpty())
        {
            Snake fittest = snakes.get(0);

            for (Snake ind : snakes) {
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

    public Snake getIndividualAt(int index)
    {
        return snakes.get(index);
    }

    public double getAverageFitness()
    {
        int sum = getTotalFitness();
        return sum/ snakes.size();
    }

    Snake getIndividualFromRoulette()
    {
        int sum = getTotalFitness();
        int rouletteSpin = (int) (Math.random() * sum);
        int currentSum = 0;

        for (Snake currentSnake : snakes) {
            currentSum += currentSnake.fitness;

            if (currentSum >= rouletteSpin) {
                return currentSnake;
            }
        }

        return null;
    }

    private int getTotalFitness()
    {
        int sum = 0;
        for (Snake snake : snakes) {
            sum += snake.fitness;
        }

        return sum;
    }

    void sortByFitness()
    {
        Collections.sort(snakes, (p1, p2) -> Double.valueOf(p2.getFitness()).compareTo(p1.getFitness()));
    }

}
