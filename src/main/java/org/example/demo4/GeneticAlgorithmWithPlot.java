package org.example.demo4;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneticAlgorithmWithPlot extends Application {

    // GA Parameters
    private static final int PopulationSizee = 101; // Number of individuals in the population during each generation ,
    // larger pop gives more diversity but in another hand it increases
    // time,smaller population converges(getting closer to a solution
    // over the time) faster but may get risky in stucking within
    // suboptimal solutions.
    private static final int ChromosomeLengthh = 32; // Length of each chromosome, which should match the length of
    // target passcode
    private static final double RateOfMutating = 0.01; // Probability of random changes(bit flippy) , 0.01% oftenly used
    // in practise so no big changes and so undetermined population
    // with more randoming and in another hand no small rates which
    // makes it slower to explore new solutions.
    private static final double RateOfCrossingOver = 0.56; // Probability of combining 2 parent's chromosomes and
    // producing offsprings. // higher rates faster exploring but
    // reduces genetic diversity , slow->slower but stable convergence --> so within is the best solution
    private static final int TopNumberOfGenerations = 1111; // Maximum number of generations so preventing running
    // indefinetly if it has no optimal sol, here weve 1111 as
    // upper limit , we chose this cuz may we need more
    // generations to get the solution(even ifs complex) , but
    // smaller num will get quicker exception of solution

    private static final Random RRandomlyCheckk = new Random(); // Random number generator
    private static final List<Integer> FitnessListStorage = new ArrayList<>(); // List to store fitness values over
    // generations

    public static void main(String[] args) {
        GeneticAlgoRepresentation(); // Perform the genetic algorithm
        launch(args);
    }

    private static void GeneticAlgoRepresentation() { // Function to execute the genetic algorithm
        int[] TargetPasscodeArr = GeneratingARANDONchromosome(ChromosomeLengthh); // Generate random target chromosome
        System.out.println("Passcode Tagrgeted : " + ConvertingChromoToStrring(TargetPasscodeArr)); // Print the target
        // passcode
//and those two lines and two methodes to get the target passcode that we want to work on

        List<int[]> chromosomePopulationList = IntializePopWithRandomChromo(); // Initialize the population with random
        // chromosomes

        int generationCounter = 0; // Initialize generation counter , inc later
        boolean TargetFpundFlag = false; // Flag to indicate if solution is found
///here the while will be running till the max number or till our target is found , looping will evaluate the fitness of each individual and selec parents for mutations and croossovers
        while (generationCounter < TopNumberOfGenerations && !TargetFpundFlag) { // Loop until max generations or
            // solution is found

            int[] ArrayForBestFitnessIndividuals = null; // Variable to store the best individual
            int BestFitnessVar = Integer.MIN_VALUE; // Variable to store the best fitness , initially aiming minimum ,
            // then when find the best high sol , then append

            for (int[] individualZ : chromosomePopulationList) { // Loop through each individual in the population
                int FitnessingVal = FitnessCalculationFunction(individualZ, TargetPasscodeArr); // Calculate fitness of
                // the individual
                if (FitnessingVal == ChromosomeLengthh) { // Check if perfect solution is found
                    System.out.println("Solution found in the number of generation: " + generationCounter);
                    System.out.println("Our Solution Chromosome : " + ConvertingChromoToStrring(individualZ));
                    TargetFpundFlag = true; // Set found flag to true initially
                    break;
                }
                // here we update best individual if current fitness is higher

                if (FitnessingVal > BestFitnessVar) { // if cuur measure greaeter thsn best one , then the individual
                    // will become the best , and its valuse stored
                    BestFitnessVar = FitnessingVal;
                    ArrayForBestFitnessIndividuals = individualZ; // here we store it
                }
            }

            if (!TargetFpundFlag) { // If solution not found
                System.out.println("Generating CCounter : " + generationCounter + " With the Best Fitness valuse: "
                        + BestFitnessVar); // Print progress
                FitnessListStorage.add(BestFitnessVar); // Track the best fitness for the generations
                chromosomePopulationList = NextGenerationCreation(chromosomePopulationList, TargetPasscodeArr); // Create
                // next
                // generation
            }

            generationCounter++; // Increment generation counter
        }

        if (!TargetFpundFlag) { // If no solution found within max generations
            System.out.println("Max generations reached without finding the passcode.");
        }
    }

    @Override
    public void start(Stage stage) { // JavaFX start method
        stage.setTitle("Genetic Algorithm Fitness Progress"); // Set window title

        NumberAxis xAxis = new NumberAxis(); // Create X-axis for the chart
        xAxis.setLabel("Generation"); // Label the X-axis

        NumberAxis yAxis = new NumberAxis(); // Create Y-axis for the chart
        yAxis.setLabel("Best Fitness"); // Label the Y-axis

        LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis); // Create the LineChart
        lineChart.setTitle("Fitness Progress Over Generations"); // Set chart title

        XYChart.Series<Number, Number> series = new XYChart.Series<>(); // Create a data series
        series.setName("Fitness Progress"); // Name the series

        for (int i = 0; i < FitnessListStorage.size(); i++) { // Populate the series with fitness data
            series.getData().add(new XYChart.Data<>(i, FitnessListStorage.get(i))); // Add data point
        }

        lineChart.getData().add(series); // Add series to the chart

        Scene scene = new Scene(lineChart, 800, 600); // Create a scene with specified size
        stage.setScene(scene); // Set the scene on the stage
        stage.show(); // Display the stage
    }

    private static int[] GeneratingARANDONchromosome(int LLeng) { // Generate a random chromosome
        int[] chromosomyy = new int[LLeng]; // Initialize chromosome array
        for (int i = 0; i < LLeng; i++) {
            chromosomyy[i] = RRandomlyCheckk.nextInt(2); // generating random binary values (0 or 1)
        }
        return chromosomyy; // Return the chromosome
    }

    private static String ConvertingChromoToStrring(int[] chromosome) { // A function in order to convert chromosome
        // array to string
        StringBuilder sb = new StringBuilder(); // Use StringBuilder for efficiency
        for (int bit : chromosome) {
            sb.append(bit); // Append each bit
        }
        return sb.toString(); // Return the string representation
    }

    private static List<int[]> IntializePopWithRandomChromo() { // Initialize population with random chromosomes
        List<int[]> ChromoPOPulation = new ArrayList<>(); // Create a list to hold the population
        for (int i = 0; i < PopulationSizee; i++) {
            ChromoPOPulation.add(GeneratingARANDONchromosome(ChromosomeLengthh)); // Add random chromosome
        }
        return ChromoPOPulation; // Return the population
    }

    private static int FitnessCalculationFunction(int[] geneArr, int[] TargetedPasscode) { // Calculate
        // fitnessfunction for
        int fitness = 0; // Initialize fitness score
        for (int i = 0; i < geneArr.length; i++) {
            if (geneArr[i] == TargetedPasscode[i]) { // Compare each bit with the target passcode to evaluate how
                // close to each others
                fitness++; // Increment fitness for each match
            }
        }
        return fitness; // Return the fitness score
    }

    private static List<int[]> NextGenerationCreation(List<int[]> PopChromo, int[] TargetedPassCodd) { // Create next
        // generation
        List<int[]> NextGenerationList = new ArrayList<>(); // Initialize list for next generation
        while (NextGenerationList.size() < PopulationSizee) { // Ensure new generation matches population size
            int[] parent1Arr = ParentSelection(PopChromo, TargetedPassCodd); // Select first parent
            int[] parent2Arr = ParentSelection(PopChromo, TargetedPassCodd); // Select second parent
            int[] childArr;
            if (RRandomlyCheckk.nextDouble() < RateOfCrossingOver) {//RRandomlyCheckk.nextDouble() -->between 0.0 and 1.0 if for ex 0.6 then 60% crossover occures
                childArr = crossover(parent1Arr, parent2Arr);
            } else {//if randome > then parent directly passed to next generation
                childArr = parent1Arr.clone();
            }
            mutate(childArr); // Apply mutation- flip 0 and 1
            NextGenerationList.add(childArr); // Add child to the next generation
        }
        return NextGenerationList; // Return the next generation
    }

    private static int[] ParentSelection(List<int[]> population, int[] passcode) { // Select a parent using tournament
        // selection
        int SelectIndividualsToBeParent = 5; // Size of the IndividualtoBeParent
        int[] best = null; // Initialize the best individual
        int bestFitness = Integer.MIN_VALUE; // Initialize best fitness
        for (int i = 0; i < SelectIndividualsToBeParent; i++) {
            int[] individual = population.get(RRandomlyCheckk.nextInt(population.size())); // Randomly select an
            // individual
            int fitness = FitnessCalculationFunction(individual, passcode); // Calculate its fitness
            if (fitness > bestFitness) { // Update best individual if fitness is higher
                bestFitness = fitness;
                best = individual;
            }
        }
        return best; // Return the best individual
    }

    private static int[] crossover(int[] P1Array, int[] P2Arr) { // Perform single-point crossover
        int[] ChildsArray = new int[P1Array.length]; // Initialize child chromosome to store offsprings genetic materials
        int crossoverPoint = RRandomlyCheckk.nextInt(P1Array.length); // Choose a random crossover point where determines where to split
        for (int i = 0; i < P1Array.length; i++) {
            if (i < crossoverPoint) {
                ChildsArray[i] = P1Array[i]; // Copy gene from parent1 if index is less than crossover point
            } else {
                ChildsArray[i] = P2Arr[i]; // Copy gene from parent2 if index is greater than or equal to crossover point

            }
        }
        return ChildsArray; // Return the child chromosome
    }

    private static void mutate(int[] individual) { // Mutate the individual chromosome
        for (int i = 0; i < individual.length; i++) {
            if (RRandomlyCheckk.nextDouble() < RateOfMutating) { // Check if mutation occurs
                individual[i] = 1 - individual[i]; // Flip the bit (0 -> 1 or 1 -> 0)
            }
        }
    }
}