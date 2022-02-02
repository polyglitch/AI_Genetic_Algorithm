package jFranz;


import java.util.*;
import java.util.Random;

public class Simulation {
	
	
	private Graph graph;
	public ArrayList<Population> generations;
	private int population_size;
	private int total_generations;
	
	//the graph is generated from reading in the file
	//the population size determines the number of children for each generation
	//total generations is the total amount of generations for the simulation
	public Simulation(Graph graph, int population_size, int total_generations) {
		this.population_size = population_size; 			//initialize the list to store the generations
		this.graph = graph;									//and store the size of the generation
		this.total_generations = total_generations;
		generations = new ArrayList<Population>(); 			
	}
	
	//iterate through the generations and generate the first population
	void runSimulation() {
		
		//generate the first population sort it and add it to the generations array
		Population first_gen = new Population(graph, population_size); 
		first_gen.firstPopulation();
		first_gen.sortPopulation();
		
		generations.add(first_gen);
		
		for (int i = 1; i <500; i++) {
			simulateGeneration(i);
		}
		//simulateGeneration(0);
		
	}
	
	void simulateGeneration(int current_number) {
		
		//
		int parent_number = current_number-1;
		
		
		//get the parent out of the array
		//and create a new population that we will populate and fill
		Population parent = generations.get(parent_number); 
		Population temp = new Population(graph, population_size);
		
		
		
		//if this breaks it is probably because pointers don't work this way
		//populate the first 10 best chromosomes from the current list
		//this is to ensure that the simulation will not get worse between previous generations
		for (int i = 0; i < 10; i++) {
			temp.addChromosome(parent.getChromosome(i));
		}
		
		
		//use generateChild to come up with Chromosomes to populate the generation with
		for (int i = 10; i < population_size; i = i +2) {
			ArrayList<Chromosome> children = generateChild(parent_number);
			temp.addChromosome(children.remove(1));
			temp.addChromosome(children.remove(0));
		}
		
		//sort the population and add it's shortest distance
		temp.sortPopulation();
		
		//add the population to the list of generations
		generations.add(temp);
			
	}
	
	//this is going to change 
	private ArrayList<Chromosome> generateChild(int parent_number) {
		
		//initialize arrays to call pickTen and pickFour
		ArrayList<Chromosome> tenlist = pickTen(parent_number);
		ArrayList<Chromosome> fourlist = pickFour(tenlist);
		
		//use random generation to randomly pick 2 parents to use for a child
		Random random = new Random();
		
		//generate a random int and use it to pull a random chromosome out of the chosen list of four
		int randomInt = random.nextInt(fourlist.size());
		Chromosome parentA = fourlist.remove(randomInt);
		randomInt = random.nextInt(fourlist.size());
		Chromosome parentB = fourlist.remove(randomInt);
		
		/*
		System.out.print("\nParents\n ParentA ");
		parentA.displayChromosome();
		System.out.print("\n ParentB ");
		parentB.displayChromosome();
		*/
		
		
		
		//choose an random crossover point
		//size is minus 2 then 1 is added so you don't choose the first or last node as the crossover
		//FIXTHIS potentially should be -3
		int crossover = random.nextInt(parentA.getSize()-2);
		crossover = crossover + 1;		
		
		//create two children
		//working
		Chromosome childA = new Chromosome(graph);
		Chromosome childB = new Chromosome(graph);
		
		
		//force the crossover
		for (int i = 0; i < parentA.getSize(); i++) {
			if (i < crossover) {
				childA.setGene(i, parentA.getGene(i));
				childB.setGene(i, parentB.getGene(i));
			}
			else  {
				childA.setGene(i, parentB.getGene(i));
				childB.setGene(i, parentA.getGene(i));
			}
		}
		
		//fix duplicates and missing in both children
		childA.fixChild();
		childB.fixChild();
		
		
		//add them to the array that will be returned
		ArrayList<Chromosome> childeren = new ArrayList<Chromosome>();
		childeren.add(childA);
		childeren.add(childB);
		
		return childeren;
		
		
	}

	//pick 10 chromosomes randomly from the list  of 500
	//and sort them by using insertion sort
	private ArrayList<Chromosome> pickTen(int parent_number) {
		
		
		//make an array to store the 10 parents
		ArrayList<Chromosome> tenlist = new ArrayList<Chromosome>();
		
		/*
		System.out.print("\ncurrent");
		System.out.print(generations.size());
		System.out.print(" parent ");
		System.out.print(parent_number);
		*/
		
		//point to the correct population to have good nodes to pull out
		Population previous = generations.get(parent_number);
		
		//generate a random number used to pick random parents
		//duplicates are okay
		Random random = new Random();
		int choice = 0;
		ArrayList<Integer> chosen = new ArrayList<Integer>();
		
		//pick 10 random chromosomes which serve as parents for the generation
		//ensure that there are no duplicates
		//sort them as they are chosen
		while (tenlist.size() < 10) {
			
			boolean isUnique = false;
			//get a random integer up to the max of the population
			
			while (!isUnique) {
				choice = random.nextInt(population_size);
				if (!chosen.contains(choice)) {
					chosen.add(choice);
					isUnique = true;
				}
			}
			
			
			
				
			//temp chromosome variable to shorten comparisons later
			Chromosome choice_chrome =  previous.getChromosome(choice);
				
			//sort the tenlist using insertion sort
			boolean place_found = false;
			int x = 0;
				
			while (!place_found) {
				//if the list is empty or x is past all current entries, add it to the end
				if (x >= tenlist.size()) {
						tenlist.add(choice_chrome);
						place_found = true;
					}
					//if it is smaller than the current entry add it and push everything down
					else if (choice_chrome.getDistance() < tenlist.get(x).getDistance()) {
						tenlist.add(x, choice_chrome);
						place_found = true;
					}
					//increment the counter
					x++;
				}
			}
			return tenlist;
		
	}

	//pull the first four elements out of the tenlist and return the list of the first four
	//this simulates using the best parents of those that were randomly chosen
	private ArrayList<Chromosome> pickFour(ArrayList<Chromosome> tenlist){
		ArrayList<Chromosome> fourlist = new ArrayList<Chromosome>();
		for (int i = 0; i < 4; i++) {
			fourlist.add(tenlist.get(i));
		}
		
		return fourlist;
	}
	
	//print the results for debugging
	void printResults() {
		generations.get(generations.size()-1).getChromosome(0).printResult();		
		System.out.print("\nFor a distance of: ");
		System.out.print(generations.get(generations.size()-1).shortestDistance());
	}
	

}
