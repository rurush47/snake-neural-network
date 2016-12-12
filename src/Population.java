import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Rurarz on 25.11.2016.
 */
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

    public void Init()
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

    public void addIndividual(Snake ind)
    {
        snakes.add(ind);
    }

    public Snake getFittest()
    {
        if(!snakes.isEmpty())
        {
            Snake fittest = snakes.get(0);

            for (int i = 0; i < snakes.size(); i++)
            {
                Snake ind = snakes.get(i);
                if (ind.fitness >= fittest.fitness)
                {
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

    public Snake getIndvividualFromRoulette()
    {
        int sum = getTotalFitness();
        int rouletteSpin = (int) (Math.random() * sum);
        int currentSum = 0;

        for (int i = 0; i < snakes.size(); i++)
        {
            Snake currentSnake = snakes.get(i);
            currentSum += currentSnake.fitness;

            if(currentSum >= rouletteSpin)
            {
                return currentSnake;
            }
        }

        return null;
    }

    public int getTotalFitness()
    {
        int sum = 0;
        for (int i = 0; i < snakes.size(); i++)
        {
            sum += snakes.get(i).fitness;
        }

        return sum;
    }

    public void sortByFitness()
    {
        Collections.sort(snakes, new Comparator<Snake>() {
            public int compare(Snake p1, Snake p2) {
                return Integer.valueOf(p2.getFitness()).compareTo(p1.getFitness());
            }
        });
    }

}
