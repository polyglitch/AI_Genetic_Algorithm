# AI_Genetic_Algorithm
A project I did for my Artificial Intelligence Class

# Goal  
The goal of this assignment is to solve the [Traveling Salesman problem](https://en.wikipedia.org/wiki/Travelling_salesman_problem). Unfortunately the Traveling Saleman problem can easily become too large to brute force. We can deal with this issue by writing a genetic algorithm. It won't give a perfect answer, but it will give one that is good enough.

# Organization
An individual list of every place visited is called a chromosome.  
Each population is made up of a list of chromosomes.  
Each simulation is a list of populations  
Graph.java parses the input into a data structure that is usable.  
TravelingSalesman.java runs the project.  


# Explanation
Each generation is made up by randomly mixing the chromosomes of some parents. The parents are picked by having the lowest score of a randomly selected group from a generation. This means that there will be randomness, but it should also cause each generation to improve. In the case that they don't, the top 10 scores from each previous generation are automatically copied over to the next one.
