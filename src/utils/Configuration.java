package utils;

import generic.SelectionMode;

/**
 * Created by Rurarz on 30.11.2016.
 */
public class Configuration {

    public static int DefaultNumberOfHiddenLayers = 2;
    public static int NumberOfInputs = 8;
    public static int NumberOfNeuronsPerLayer = 6;
    public static int NumberOfOutputs = 1;
    public static int Bias = -1;
    public static int ActivationResponse = 1;
    public static double MaxPerturbation = 0.12;
    public static double CrossoverRate = 0.7;
    public static double MutationRate = 0.09;
    public static int TournamentSelectionSize = 5;
    public static boolean Elitism = true;
    public static int ElitismSize = 3;
    public static SelectionMode Mode = SelectionMode.TOURNAMENT;
    public static int PopulationSize = 50;
    public static int CyclesPerGeneration = 200;
}
