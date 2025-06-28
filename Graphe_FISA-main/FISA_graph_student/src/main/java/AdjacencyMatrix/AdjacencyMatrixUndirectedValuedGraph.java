package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;

public class AdjacencyMatrixUndirectedValuedGraph extends AdjacencyMatrixUndirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixUndirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}


	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------	
	
	
	/**
     * adds the edge (x,y,cost). If there is already one initial cost, we replace it.
     */
	public void addEdge(int x, int y, int cost ) {
		if(!isEdge(x, y)) {
			this.matrix[x][y] = cost;
			this.matrix[y][x] = cost;
			changeNbEdges(1);
		}
	}

	public Integer getCost(int x, int y) {
		if (this.isEdge(x, y)) {
			return this.matrix[x][y];
		} else {
			System.out.println("There is no edge between " + x + " and " + y);
			return null;
		}
	}
	
	public String toString() {
		StringBuilder s = new StringBuilder("\n Matrix of Costs: \n");
		for (int[] lineCost : this.matrix) {
			for (int i : lineCost) {
				s.append(i).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}
	
	
	public static void main(String[] args) {
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		AdjacencyMatrixUndirectedValuedGraph am = new AdjacencyMatrixUndirectedValuedGraph(matrixValued);
		System.out.println(am);

		// Is there an edge between 0 and 2 ?
		System.out.println("\n\nDoit être vrai : isEdge(0, 2) ? " + am.isEdge(0, 2));
		Integer cost = am.getCost(0, 2);
		System.out.println("Doit être 8 : Côt de l'arête {0,2} : " + cost);

		// We add one edge {3,5} :
		System.out.println("\n\nDoit être faux : isEdge(3, 5) ? " + am.isEdge(3, 5));
		// We add one edge {3,5} :
		System.out.println("\n\nAjout de l'arête {3,5} avec un coût de 77 :");
		am.addEdge(3, 5, 77);
		System.out.println("\n\nDoit être vrai : isEdge(3, 5) ? " + am.isEdge(3, 5));
		cost = am.getCost(3, 5);
		System.out.println("Doit être 77 : Coût de l'arête {3,5} : " + cost);
		System.out.println(am);

		// We remove the edge {3,5} :
		System.out.println("\nAprès avoir supprimé l'arête {3,5} :");
		am.removeEdge(3,5);
		System.out.println("\n\nDoit être faux : isEdge(3, 5) ? " + am.isEdge(3, 5));
		System.out.println(am);
	}

}
