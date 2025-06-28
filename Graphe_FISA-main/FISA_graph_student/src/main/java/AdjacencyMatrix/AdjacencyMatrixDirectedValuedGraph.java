package AdjacencyMatrix;

import GraphAlgorithms.GraphTools;

public class AdjacencyMatrixDirectedValuedGraph extends AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
	// 				Class variables
	//-------------------------------------------------- 

	// No class variable, we use the matrix variable but with costs values 

	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

	public AdjacencyMatrixDirectedValuedGraph(int[][] matrixVal) {
		super(matrixVal);
	}

	
	// ------------------------------------------------
	// 					Methods
	// ------------------------------------------------	
	
	
	/**
     * adds the arc (from,to,cost). If there is already one initial cost, we replace it.
     */	
	public void addArc(int from, int to, int cost ) {
		if (!this.isArc(from, to)) {
			this.matrix[from][to] = cost;
			this.nbArcs++;
		} else {
			System.out.println("There is already an arc from " + from + " to " + to);
		}
	}

	public Integer getCost(int from, int to) {
		if (this.isArc(from, to)) {
			return this.matrix[from][to];
		} else {
			System.out.println("There is no arc from " + from + " to " + to);
			return null;
		}
	}

	
	@Override
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
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
		AdjacencyMatrixDirectedValuedGraph am = new AdjacencyMatrixDirectedValuedGraph(matrixValued);
		System.out.println("Seed used to have the following results : 100001");
		System.out.println(am);

		// Is Edge from 0 to 3 :
		System.out.println("\n\nDoit être faux : Il y a un arc entre 0 et 3 ? : " + am.isArc(0, 3));
		System.out.println("\n\nDoit être vrai : Il y a un arc entre 0 et 3 ? : " + am.isArc(0, 2));
		Integer cost = am.getCost(0, 2);
		System.out.println("Doit être 13 : Coût de l'arc entre 0 et 2 : " + cost);

		// We add an arc from 0 to 2 :
		System.out.println("\n\nNous ajoutons un arc entre 0 et 2 et entre 0 et 3 : ");
		am.addArc(0, 2, 47);
		am.addArc(0, 3, 88);
		System.out.println("\nDoit être vrai : Il y a un arc entre 0 et 2 : " + am.isArc(0, 2));
		cost = am.getCost(0, 2);
		System.out.println("Doit être 13 : Coût de l'arc entre 0 et 2 : " + cost);
		System.out.println("\nDoit être vrai : Il y a un arc entre 0 et 3 ? : " + am.isArc(0, 3));
		cost = am.getCost(0, 3);
		System.out.println("Doit être 88 : Coût de l'arc entre 0 et 3 : " + cost);

		// We remove the arc from 0 to 2 :
		System.out.println("\n\nNous supprimons l'arc entre 0 et 2 : ");
		am.removeArc(0, 2);
		System.out.println(am);
		System.out.println("\nDoit être faux : Il y a un arc entre 0 et 2 ? : " + am.isArc(0, 2));

		am.displayGraphStream();
	}
}
