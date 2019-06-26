package generic;


import main.EvolutionController;

import java.util.ArrayList;

public class GenericAlgorithm
{
    private static double crossoverRate = EvolutionController.Config.CrossoverRate;
    private static double mutationRate = EvolutionController.Config.MutationRate;
    private static int tournamentSelectionSize = EvolutionController.Config.TournamentSelectionSize;
    private static boolean elitism = EvolutionController.Config.Elitism;
    private static int elitismSize = EvolutionController.Config.ElitismSize;
    private static SelectionMode Mode = EvolutionController.Config.Mode;

    public static Population evolve(Population population)
            throws IllegalAccessException, InstantiationException
    {
        int populationSize = population.getSize();
        Population newPopulation = new Population(populationSize, false, population.getGenericType());

        int offset = 0;

        if (elitism)
        {
            if(elitismSize == 1)
            {
                Individual fittest = population.getFittest();
                fittest.fitness = 0;
                Individual fittestS = fittest;
                newPopulation.addIndividual(fittestS);
                offset = 1;
            }
            else
            {
                population.sortByFitness();
                offset = elitismSize;
                for (int i = 0; i < elitismSize; i++)
                {
                    Individual newIndividual = population.getIndividualAt(i);
                    newIndividual.fitness = 0;
                    Individual newIndividualS = newIndividual;
                    newPopulation.addIndividual(newIndividualS);
                }
            }
        }

        if(Mode == SelectionMode.TOURNAMENT)
        {
            for (int i = offset; i < populationSize; i++)
            {
                Individual ind1 = tournamentSelection(population);
                Individual ind2 = tournamentSelection(population);
                Individual newIndividual = crossoverIndividuals(ind1, ind2, population.getGenericType());

                newPopulation.addIndividual(newIndividual);
            }
        }
        else if(Mode == SelectionMode.ROULETTE)
        {
            for (int i = offset; i < populationSize; i++) {
                Individual ind1 = rouletteSelection(population);
                Individual ind2 = rouletteSelection(population);
                Individual newIndividual = crossoverIndividuals(ind1, ind2, population.getGenericType());

                newPopulation.addIndividual(newIndividual);
            }
        }

        //mutate population
        for (int i = offset; i < populationSize; i++)
        {
            Individual ind = newPopulation.getIndividualAt(i);
            mutate(ind);
        }

        return newPopulation;
    }

    private static Individual crossoverIndividuals(Individual ind1, Individual ind2, Class type)
            throws IllegalAccessException, InstantiationException {
        Individual newBorn = (Individual) type.newInstance();

        ArrayList<Object> newGenome = new ArrayList<>();
        ArrayList<Object> genome1 = ind1.getGenome();
        ArrayList<Object> genome2 = ind2.getGenome();

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

    private static void mutate(Individual individual)
    {
        ArrayList<Object> genome = individual.getGenome();

        for (int i = 0; i < genome.size(); i++)
        {
            if(Math.random() <= mutationRate)
            {
                Object newGene = genome.get(i);
                newGene = individual.mutateGene(newGene);
                genome.remove(i);
                genome.add(i, newGene);
            }
        }

        individual.applyGenome(genome);
    }

    private static Individual tournamentSelection(Population population)
            throws IllegalAccessException, InstantiationException
    {
        Population tournament = new Population(tournamentSelectionSize, false, population.getGenericType());

        for (int i = 0; i < tournamentSelectionSize; i++) {
            int randomId = (int) (Math.random() * population.getSize());
            tournament.addIndividual(population.getIndividualAt(randomId));
        }

        return tournament.getFittest();
    }

    private static Individual rouletteSelection(Population population)
    {
        return population.getIndividualFromRoulette();
    }
}
