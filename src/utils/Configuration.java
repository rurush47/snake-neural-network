package utils;

import generic.SelectionMode;

public class Configuration
{
    public static String ConfigPath = "config.txt";
    public String PopulationSavePath = "network.txt";
    public int DefaultNumberOfHiddenLayers = 1;
    public int NumberOfInputs = 5;
    public int NumberOfNeuronsPerLayer = 8;
    public int NumberOfOutputs = 1;
    public int Bias = -1;
    public int ActivationResponse = 1;
    public double MaxPerturbation = 0.1;
    public double CrossoverRate = 0.6;
    public double MutationRate = 0.1;
    public int TournamentSelectionSize = 5;
    public boolean Elitism = true;
    public int ElitismSize = 4;
    public SelectionMode Mode = SelectionMode.TOURNAMENT;
    public int PopulationSize = 70;
    public int CyclesPerGeneration = 200;
}
