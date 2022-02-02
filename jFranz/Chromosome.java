package jFranz;

import java.util.*;
import java.util.Random;

public class Chromosome {
	private int size;
	private int distance_size;
	public ArrayList<Integer> genes;
	private int total_distance;
	private Graph graph;
	
	public Chromosome(Graph graph) {
		this.graph = graph;
		size = graph.getSize(); //size of the graph
		
		distance_size = size-1; //size of matrix
		
		size = size + 1; //size of the array
		
		
		genes = new ArrayList<Integer>(size); //list of genes or cities
		for (int i = 0; i < size; i++) {
			genes.add(0);
		}
		
		total_distance = 0;
		
		randomize();
		
		calculateDistance();
		
		//System.out.print("\n distance: ");
		//System.out.print(total_distance);
		
	}
	/*
	public Chromosome(Graph graph, boolean bool) {
		
		this.graph = graph;
		size = graph.getSize(); //size of the graph
		
		distance_size = size-1; //size of matrix
		
		size = size + 1;
		
		
		genes = new ArrayList<Integer>(size); //list of genes or cities
		for (int i = 0; i < size; i++) {
			genes.add(0);
		}
		
		total_distance = 0;
		
		calculateDistance();
		
	}
	*/
	
	void randomize() { 	//randomly assign values for the traveling
						//use a bag randomizer like tetris
		LinkedList<Integer> bag = new LinkedList<Integer>();
		
		//System.out.println();
		
		//fill bag of the bag randomizer
		for (int i = 1; i < size-1; i++) {
			bag.add(i);
		}
		
		//randomly pull elements out of the bag until it is empty
		Random random = new Random();
		for (int i = 1; i < size-1; i++) {
			int temp = random.nextInt(bag.size());
			temp = bag.remove(temp);
			
			genes.set(i, temp);
		}
		
		//
	}
	
	void calculateDistance() {
		int temp = 0;
		int y = 0;
		for (int x = 0; x<size-2; x++) { 	//iterate through genes
			y = x + 1;
			temp = graph.getDistance(genes.get(x), genes.get(y)); 	//get the distance from graph using the randomly
															//assigned numbers in gene
			total_distance = total_distance + temp; 		//sum the total distanes
		}
		
		//flip distance because it needs to be inverted
	}
	
	
	//return the total distance traveled
	int getDistance() { 
		return total_distance;
	}
	
	//return the number of elements in the chromosome
	int getSize() {
		return size;
	}
	
	Integer getGene(int position) {
		int gene = genes.get(position);
		return gene;
	}
	
	void setGene(int position, int value) {
		genes.set(position, value);
	}
	
	void displayChromosome() {
		//change 5 back to size
		//System.out.print("\n Chromosome: ");
		System.out.print("Chromosome: ");
		for (int i = 0; i < size; i ++) {
			System.out.print(genes.get(i));
			System.out.print(" ");
			
		}
	}
	
	void printResult() {
		//change 5 back to size
		//System.out.print("\n Chromosome: ");
		System.out.print("\nI traveled cities: ");
		for (int i = 0; i < size; i ++) {
			System.out.print(genes.get(i));
			if (i < size-1)
			System.out.print(", ");
			
		}
	}
	
	void fixChild() {
		
		ArrayList<Integer> duplicates = new ArrayList<Integer>();
		ArrayList<Integer> visited = new ArrayList<Integer>();
		ArrayList<Integer> all = new ArrayList<Integer>();
		LinkedList<Integer> missing = new LinkedList<Integer>();
		
		
		//populate a linked list with all possible cities to give us a way to check for missing cities
		for (int i = 1; i < genes.size()-1; i++) {
			missing.add(i);
		}
		
		//find the missing and duplicate cities
		for (int i = 1; i < genes.size()-1; i++) {
			
			//places the currently city number in current to reduce calls
			int current = genes.get(i);
			
			int result = missing.indexOf(current);
			
			if (result == -1) {
				duplicates.add(current);
			}
			else {
				int index = missing.indexOf(current);
				missing.remove(index);
			}
			
		}	
		
		//randomly choose from the missing elements and swap them in on duplicates
		Random random = new Random();
		for(int i = 0; !missing.isEmpty(); i++) {
			int swap = missing.remove(random.nextInt(missing.size()));
			int thing_to_swap = duplicates.remove(random.nextInt(duplicates.size()));
			
			//int swap = missing.remove();
			int position = genes.indexOf(thing_to_swap);
			genes.set(position, swap);
		}
	}
}
