package jFranz;

import java.util.*;
import java.io.*;

class Graph {

	private ArrayList<String> cities = new ArrayList<String>();
	private int[][] distance;
	private int size;
	
	public Graph() {
		// TODO Auto-generated constructor stub
		
	}
	
	public Graph(String args[]) {
		//String curr_arguement = args[0];
		parseCities(args[0]);
		
		size = cities.size();
		distance = new int[size][size];
		
		fillMatrix();
		
		
		//curr_arguement = args[1];
		
		parseDistances(args[1]);
		
		//printMatrix(`);
	}
	
	private void parseCities(String filename) { //method to parse the cities
		File f = new File(filename); //set the file to be used
			try {
				Scanner sc = new Scanner(f);
				while (sc.hasNextLine())   		//while  there is another line to parse
					cities.add(sc.nextLine());	//parse it and add it to the list of cities
				sc.close();	
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("file not found");
			}
	}
	
	private void parseDistances(String filename) { //method to parse the distances of the cities
		File f = new File(filename); //set the file to be used
			try {
				Scanner sc = new Scanner(f);
				int x = 0; 		
				int y = 0;
				int tmp;
				String temp;
				
				
				while (sc.hasNext()) {
					x = cities.indexOf(sc.next());
					y = cities.indexOf(sc.next());
					tmp = sc.nextInt();
					
					distance[x][y] = tmp;
					distance[y][x] = tmp;
				}
				sc.close();
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				System.out.println("file not found");
			}
		//FileReader fr = new FileReader("resources/cities.txt");
		
		
	}
	private void fillMatrix() {  //go through the matrix and fill it with -1s
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				distance[x][y] = -1;
			}
		}
	}
	private void printMatrix() { //print out the contents of the distance matrix
		for (int y = 0; y < size; y++) {
			System.out.print('\n');
			for (int x = 0; x < size; x++) {
				System.out.print(distance[x][y]);
				System.out.print(" ");
			}
		}
	}
	
	public int getPosition(String name) {  //find the position of the city that corresponds to the given name
		return cities.indexOf(name);
	}
	
	public String getCity(int number) {    //find the name of the city that corresponds to that postion
		return cities.get(number);
	}
	
	public int getDistance(int x, int y) { //pass an x and y value to find the distance between
									//the cities that correspond between those locations
		return distance[x][y];
	}
	
	public int getSize() { //return the size of the graph
		return size;
	}
}
