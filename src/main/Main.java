package main;

import generic.GenericAlgorithm;
import generic.Population;
import snake.Snake;
import utils.Configuration;
import view.EvolutionView;

import java.util.Timer;

public class Main
{
    public static void main(String[] args) throws Exception {

        EvolutionView view = new EvolutionView();
        EvolutionController evolutionController = new EvolutionController(view);
        Population initialPopulation = new Population<>(Configuration.PopulationSize, true, Snake.class);

        int counter = 0;

        while (true)
        {
            if(evolutionController.continueEvolution)
            {
                //how many moves before evolution
                for(int i = 0; i < Configuration.CyclesPerGeneration; i++)
                {
                    boolean allDeath = true;
                    //for each snake make move
                    for (int j = 0; j < initialPopulation.getSize(); j++)
                    {
                        Snake ind = (Snake) initialPopulation.getIndividualAt(j);
                        if(!ind.getMap().death)
                        {
                            allDeath = false;
                            ind.update();
                        }
                    }

                    if (allDeath)
                    {
                        break;
                    }
                }
                counter++;
                System.out.println(initialPopulation.getAverageFitness() + "  ||  "
                        + String.format( "%.2f", (double) counter));

                if (Configuration.Elitism)
                {
                    if(Configuration.ElitismSize == 1)
                    {
                        Snake snake = (Snake) initialPopulation.getFittest();
                        snake.resetMap();
                    }
                    else
                    {
                        initialPopulation.sortByFitness();
                        for (int i = 0; i < Configuration.ElitismSize; i++)
                        {
                            Snake snake = (Snake) initialPopulation.getIndividualAt(i);
                            snake.resetMap();
                        }
                    }
                }

                initialPopulation = GenericAlgorithm.evolve(initialPopulation);
            }
            else
            {
                break;
            }
        }

        for(int i = 0; i < Configuration.CyclesPerGeneration; i++)
        {
            //for each snake make move
            for (int j = 0; j < initialPopulation.getSize(); j++)
            {
                Snake snake = (Snake) initialPopulation.getIndividualAt(j);
                snake.update();
            }
        }

        Snake best = (Snake) initialPopulation.getFittest();


        Controller controller = new Controller(best);
        Timer timer = new Timer();
        timer.schedule(controller, 0, 200);
    }
}
