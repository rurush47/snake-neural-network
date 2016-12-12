import java.util.Vector;

/**
 * Created by Rurarz on 25.11.2016.
 */
public class GenericAlgorithm {

    enum SelectionMode {TOURNAMENT, ROULETTE};

    static double crossoverRate = Configuration.CrossoverRate;
    static double mutationRate = Configuration.MutationRate;
    static int tournamentSelectionSize = Configuration.TournamentSelectionSize;
    static boolean elitism = Configuration.Elitism;
    static int elitismSize = Configuration.ElitismSize;
    public static SelectionMode Mode = Configuration.Mode;


    public static Population evolve(Population population)
    {
        int populationSize = population.getSize();
        Population newPopulation = new Population(populationSize, false);

        int offset = 0;

        if (elitism)
        {
            if(elitismSize == 1)
            {
                Snake fittest = population.getFittest();
                fittest.fitness = 0;
                fittest.getMap().death = false;
                newPopulation.addIndividual(fittest);
                offset = 1;
            }
            else
            {
                population.sortByFitness();
                offset = elitismSize;
                for (int i = 0; i < elitismSize; i++)
                {
                    Snake newSnake = population.getIndividualAt(i);
                    newSnake.fitness = 0;
                    newSnake.getMap().death = false;
                    newPopulation.addIndividual(newSnake);
                }
            }
        }

        if(Mode == SelectionMode.TOURNAMENT)
        {
            for (int i = offset; i < populationSize; i++)
            {
                Snake ind1 = tournamentSelection(population);
                Snake ind2 = tournamentSelection(population);
                Snake newSnake = crossoverIndividuals(ind1, ind2);

                newPopulation.addIndividual(newSnake);
            }
        }
        else if(Mode == SelectionMode.ROULETTE)
        {
            for (int i = offset; i < populationSize; i++) {
                Snake ind1 = rouletteSelection(population);
                Snake ind2 = rouletteSelection(population);
                Snake newSnake = crossoverIndividuals(ind1, ind2);

                newPopulation.addIndividual(newSnake);
            }
        }

        //mutate population
        for (int i = offset; i < populationSize; i++)
        {
            Snake ind = newPopulation.getIndividualAt(i);
            mutate(ind);
        }

        return newPopulation;
    }

    public static Snake crossoverIndividuals(Snake ind1, Snake ind2)
    {
        Snake newBorn = new Snake();

        Vector<Double> newGenome = new Vector<>();
        Vector<Double> genome1 = ind1.getGenome();
        Vector<Double> genome2 = ind2.getGenome();

        for(int i = 0; i < genome1.size(); i++)
        {
            if(Math.random() <= crossoverRate)
            {
                newGenome.add(genome1.get(i));
            }
            else
            {
                newGenome.add(genome2.get(i));
            }
        }

        newBorn.applyGenome(newGenome);

        return newBorn;
    }

    public static void mutate(Snake snake)
    {
        Vector<Double> genome = snake.getGenome();

        for (int i = 0; i < genome.size(); i++)
        {
            if(Math.random() <= mutationRate)
            {
                double newDouble = genome.get(i);
                newDouble += Neuron.RandomDouble() * Configuration.MaxPerturbation;
                genome.remove(i);
                genome.add(i, newDouble);
            }
        }

        snake.applyGenome(genome);
    }

    public static Snake tournamentSelection(Population population)
    {
        Population tournament = new Population(tournamentSelectionSize, false);

        for (int i = 0; i < tournamentSelectionSize; i++) {
            int randomId = (int) (Math.random() * population.getSize());
            tournament.addIndividual(population.getIndividualAt(randomId));
        }

        return tournament.getFittest();
    }

    public static Snake rouletteSelection(Population population)
    {
        return population.getIndvividualFromRoulette();
    }
}
