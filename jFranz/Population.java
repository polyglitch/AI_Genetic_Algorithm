package jFranz;

import java.util.*;

public class Population {

	private Graph graph;
	private ArrayList<Chromosome> population;
	private int population_size;
	private int size;
	
	public Population(Graph graph, int population_size) {
		this.population_size = population_size; 			//initialize the list to store the generations
		this.graph = graph;									//and store the size of the generation
		population = new ArrayList<Chromosome>(); 			
	}
	
	void firstPopulation() {
		for (int i = 0; i < population_size; i++) {//generate a bunch of random chromosomes for the first generation
		population.add(new Chromosome(graph));
		}
	}
	
	void sortPopulation() {
		int low = 0; 							//find max and min for pivot algorithm
		int high = population_size-1; 			//# of elements in the population
		
		sort(low,high);
	}
	private void sort(int low, int high) { 		//recursive sort function

		if (low < high) {
			int pivot = partition(low, high);
			
			sort(low, pivot - 1);
			sort(pivot+1, high);
		}
	}
	
	private int partition(int low, int high) {	//partition function for sort
		int pivot = population.get(high).getDistance();
		
		int x = (low - 1);
		
		for (int y = low; y <= high - 1 ; y++) {
			
			if (population.get(y).getDistance() < pivot) {
				x++;
				swapChromes(x, y);
			}
			
		}
		x = x+1;
		swapChromes(x, high);
		return (x);
		
	}
	
	void swapChromes(int a, int b) { 			//swap the two chromosomes at the supplied positions
		Chromosome temp = population.get(a);
		population.set(a, population.get(b));
		population.set(b, temp);
	}
	
	void printPopulation() {					//print out the entire population
		
		for (int i = 0; i < population_size; i++) {
			System.out.print("\n  number");
			System.out.print(i);
			System.out.print("  ");
			System.out.print(population.get(i).getDistance());
		}
	}
	
	//allows you to fetch a chromosome out of the arraylist
	Chromosome getChromosome(int number) {
		return population.get(number);
	}
	
	//lets you add a chromosome to the arraylist
	void addChromosome(Chromosome chrome) {
		population.add(chrome);
	}
	
	//returns the shortest distance by returning the distance of the first chromosome
	//only returns the correct answer if the generation has been sorted
	int shortestDistance() {
		return population.get(0).getDistance();
	}
	
	int getSize() {
		return population.size();
	}
}
