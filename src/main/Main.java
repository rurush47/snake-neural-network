package main;

import com.google.gson.Gson;
import generic.GenericAlgorithm;
import generic.Population;
import snake.Snake;
import utils.Configuration;
import view.EvolutionView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Timer;

public class Main
{
    public static void main(String[] args) throws Exception
    {
//        File f = new File(Configuration.ConfigPath);
//        if(f.exists() && !f.isDirectory())
//        {
//
//        }
//        else
//        {
//            Gson gson = new Gson();
//            String json = gson.toJson(new Configuration());
//
//            try (PrintWriter out = new PrintWriter(Configuration.ConfigPath)) {
//                out.println(json);
//            } catch (FileNotFoundException ex) {
//                ex.printStackTrace();
//            }
//        }

        EvolutionView view = new EvolutionView();
        EvolutionController evolutionController = new EvolutionController(view);

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
                    for (int j = 0; j < evolutionController.population.getSize(); j++)
                    {
                        Snake ind = (Snake) evolutionController.population.getIndividualAt(j);
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
                System.out.println(evolutionController.population.getAverageFitness() + "  ||  "
                        + String.format( "%.2f", (double) counter));

                if (Configuration.Elitism)
                {
                    if(Configuration.ElitismSize == 1)
                    {
                        Snake snake = (Snake) evolutionController.population.getFittest();
                        snake.resetMap();
                    }
                    else
                    {
                        evolutionController.population.sortByFitness();
                        for (int i = 0; i < Configuration.ElitismSize; i++)
                        {
                            Snake snake = (Snake) evolutionController.population.getIndividualAt(i);
                            snake.resetMap();
                        }
                    }
                }

                evolutionController.population = GenericAlgorithm.evolve(evolutionController.population);
            }
            else
            {
                break;
            }
        }

        for(int i = 0; i < Configuration.CyclesPerGeneration; i++)
        {
            //for each snake make move
            for (int j = 0; j < evolutionController.population.getSize(); j++)
            {
                Snake snake = (Snake) evolutionController.population.getIndividualAt(j);
                snake.update();
            }
        }

        Snake best = (Snake) evolutionController.population.getFittest();


        Controller controller = new Controller(best);
        Timer timer = new Timer();
        timer.schedule(controller, 0, 200);
    }
}
