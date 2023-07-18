package dynamic_programming_best_path.models;



public class Edge {
    public String destinationNodeName;
    public Double cost;

    public Edge(String destinationNodeName, Double cost) {
        this.destinationNodeName = destinationNodeName;
        this.cost = cost;
    }

    @Override
    public String toString() {
       return "("+ destinationNodeName+", "+ cost+")";

    }

}
