package AdjacencyList;

import java.util.List;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class AdjacencyListUndirectedValuedGraph extends AdjacencyListUndirectedGraph{

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

    public AdjacencyListUndirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new UndirectedNode(i));            
        }
        for (UndirectedNode n1 : this.getNodes()) {
            for (int j = n1.getLabel(); j < matrixVal[n1.getLabel()].length; j++) {
            	UndirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Edge e1 = new Edge(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addEdge(e1);
                    this.edges.add(e1);
                	n2.addEdge(new Edge(n2,n1,matrixVal[n1.getLabel()][j]));
                    this.nbEdges++;
                }
            }
        }
    }

    //--------------------------------------------------
    // 				Methods
    //--------------------------------------------------
    

    /**
     * Adds the edge (from,to) with cost if it is not already present in the graph.
     * And adds this edge to the incident list of both extremities (nodes) and into the global list "edges" of the graph.
     */
    public void addEdge(UndirectedNode x, UndirectedNode y, int cost) {
        if(!isEdge(x,y)){
            Edge e1 = new Edge(x, y, cost);
            this.edges.add(e1);
            this.getNodeOfList(x).addEdge(e1);
            this.getNodeOfList(y).addEdge(e1);
            this.nbEdges++;
        }
    }

    public Integer getWeight(UndirectedNode x, UndirectedNode y) {
        if (isEdge(x, y)) {
            List<Edge> edges = this.getNodeOfList(x).getIncidentEdges();
            for (Edge e : edges) {
                if (e.getSecondNode().equals(y)) {
                    return e.getWeight();
                }
            }
        }
        return null;
    }
    
    
    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListUndirectedValuedGraph al = new AdjacencyListUndirectedValuedGraph(matrixValued);
        System.out.println("Seed used to have the following results : 100001");
        System.out.println(al);
        System.out.println("Doit être faux : (n_2,n_5) est dans le graphe ? " +  al.isEdge(al.getNodes().get(2), al.getNodes().get(5)));

        UndirectedNode n_0 = new UndirectedNode(0);
        UndirectedNode n_2 = new UndirectedNode(2);
        UndirectedNode n_5 = new UndirectedNode(5);
        al.addEdge(n_2, n_5, 666);
        al.addEdge(n_2, n_0, 44);
        System.out.println("Ajout des arrêtes entre 0 et 2 ainsi qu'entre 2 et 5.");

        System.out.println("Doit être vrai : (n_2,n_5) est dans le graphe ? " +  al.isEdge(n_2, n_5));
        System.out.println("Doit être vrai : (n_0,n_2) est dans le graphe ? " +  al.isEdge(n_0, n_2));
        Integer weight = al.getWeight(n_2, n_5);
        System.out.println("Doit être 666 : Le poids de l'arête (n_2,n_5) est " + weight);
        weight = al.getWeight(n_2, n_0);
        System.out.println("Doit être 8 (valeur initiale car nous ne pouvons pas remplacer le poids existant) : Le poids de l'arête (n_2,n_0) est " + weight);

        al.removeEdge(n_5, n_2);
        al.removeEdge(n_2, n_0);
        System.out.println("Suppression des arrêtes entre 0 et 2 ainsi qu'entre 2 et 5.");

        System.out.println("Doit être faux : (n_2,n_5) est dans le graphe ? " +  al.isEdge(n_2, n_5));
        System.out.println("Doit être faux : (n_0,n_2) est dans le graphe ? " +  al.isEdge(n_0, n_2));

    }
}
