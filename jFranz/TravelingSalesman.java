package jFranz;

public class TravelingSalesman {

	public TravelingSalesman() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		Graph graph = new Graph(args);
		
		int pop_size = Integer.parseInt(args[2]); 			//parse string args into integers to pass them
		int num_generations = Integer.parseInt(args[3]);
				
		Simulation simulation = new Simulation(graph, pop_size, num_generations);
		simulation.runSimulation();
		simulation.printResults();
		
	}

}
