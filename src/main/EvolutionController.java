package main;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import generic.Population;
import neural_network.NeuralNet;
import snake.Snake;
import utils.Configuration;
import view.EvolutionView;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class EvolutionController implements KeyListener
{
    Population population;
    boolean continueEvolution = true;

    EvolutionController(EvolutionView view)
    {
        view.addListener(this);

        try {
            this.population = new Population<>(Configuration.PopulationSize, true, Snake.class);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void keyTyped(KeyEvent e)
    {

    }

    private void SerializePopulation()
    {
        ArrayList<Snake> snakes = population.getIndividuals();
        List<NeuralNet> brains = snakes.stream().map(x -> x.getNeuralNet()).collect(Collectors.toList());

        Gson gson = new Gson();
        String json = gson.toJson(brains);

        try (PrintWriter out = new PrintWriter(Configuration.PopulationSavePath)) {
            out.println(json);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    private Population DeserializePopulation()
    {
        String contents = "";
        try {
            contents = new String(Files.readAllBytes(Paths.get(Configuration.PopulationSavePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();

        Type listType = new TypeToken<ArrayList<NeuralNet>>(){}.getType();
        ArrayList<NeuralNet> brains = gson.fromJson(contents, listType);

        ArrayList<Snake> snakes =
                brains.stream().map(x -> new Snake(x)).collect(Collectors.toCollection(ArrayList::new));

        Population p = new Population(snakes, Snake.class);

        return p;
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_S)
        {
            continueEvolution = false;
        }
        if(e.getKeyCode() == KeyEvent.VK_J)
        {
            SerializePopulation();
        }
        if(e.getKeyCode() == KeyEvent.VK_K)
        {
            population = DeserializePopulation();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
