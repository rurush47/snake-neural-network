package utils;

import generic.SelectionMode;

public class Configuration
{
    public static String PopulationSavePath = "network.txt";
    public static String ConfigPath = "config.txt";
    public static int DefaultNumberOfHiddenLayers = 1;
    public static int NumberOfInputs = 5;
    public static int NumberOfNeuronsPerLayer = 8;
    public static int NumberOfOutputs = 1;
    public static int Bias = -1;
    public static int ActivationResponse = 1;
    public static double MaxPerturbation = 0.1;
    public static double CrossoverRate = 0.6;
    public static double MutationRate = 0.1;
    public static int TournamentSelectionSize = 5;
    public static boolean Elitism = true;
    public static int ElitismSize = 4;
    public static SelectionMode Mode = SelectionMode.TOURNAMENT;
    public static int PopulationSize = 70;
    public static int CyclesPerGeneration = 200;
}
