package AdjacencyMatrix;


import GraphAlgorithms.GraphTools;
import Nodes_Edges.AbstractNode;
import Nodes_Edges.DirectedNode;

import java.util.ArrayList;
import java.util.List;

import AdjacencyList.AdjacencyListDirectedGraph;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.SingleGraph;

/**
 * This class represents the directed graphs structured by an adjacency matrix.
 * We consider only simple graph
 */
public class AdjacencyMatrixDirectedGraph {

	//--------------------------------------------------
    // 				Class variables
    //--------------------------------------------------

    protected int nbNodes;		// Number of vertices
    protected int nbArcs;		// Number of edges/arcs
    protected int[][] matrix;	// The adjacency matrix
	
	//--------------------------------------------------
	// 				Constructors
	//-------------------------------------------------- 

    public AdjacencyMatrixDirectedGraph() {
        this.matrix = new int[0][0];
        this.nbNodes = 0;
        this.nbArcs = 0;
    }

      
	public AdjacencyMatrixDirectedGraph(int[][] mat) {
		this.nbNodes = mat.length;
		this.nbArcs = 0;
		this.matrix = new int[this.nbNodes][this.nbNodes];
		for(int i = 0; i<this.nbNodes; i++){
			for(int j = 0; j<this.nbNodes; j++){
				this.matrix[i][j] = mat[i][j];
				this.nbArcs += mat[i][j];
			}
		}
	}

	public AdjacencyMatrixDirectedGraph(AdjacencyListDirectedGraph g) {
		this.nbNodes = g.getNbNodes();
		this.nbArcs = g.getNbArcs();
		this.matrix = g.toAdjacencyMatrix();
	}

	public void displayGraphStream() {
		System.setProperty("org.graphstream.ui", "swing");

		Graph graph = new SingleGraph("Directed Graph");

		// Ajout des noeuds
		for (int i = 0; i < nbNodes; i++) {
			graph.addNode(String.valueOf(i)).setAttribute("ui.label", i);
		}

		// Ajout des arcs orientés
		for (int from = 0; from < nbNodes; from++) {
			for (int to = 0; to < nbNodes; to++) {
				if (matrix[from][to] > 0) {
					String edgeId = from + "->" + to;
					if (graph.getEdge(edgeId) == null) {
						graph.addEdge(edgeId, String.valueOf(from), String.valueOf(to), true);
					}
				}
			}
		}

		graph.setAttribute("ui.stylesheet", """
        node {
            fill-color: #4FBDBA;
            size: 25px;
            text-size: 18px;
            text-alignment: center;
        }
        edge {
            arrow-shape: arrow;
            arrow-size: 15px, 6px;
            fill-color: #7D6B91;
        }
    """);

		graph.display();
	}

	//--------------------------------------------------
	// 					Accessors
	//--------------------------------------------------


    /**
     * Returns the matrix modeling the graph
     */
    public int[][] getMatrix() {
        return this.matrix;
    }

    /**
     * Returns the number of nodes in the graph (referred to as the order of the graph)
     */
    public int getNbNodes() {
        return this.nbNodes;
    }
	
    /**
	 * @return the number of arcs in the graph
 	 */	
	public int getNbArcs() {
		return this.nbArcs;
	}

	
	/**
	 * @param u the vertex selected
	 * @return a list of vertices which are the successors of u
	 */
	public List<Integer> getSuccessors(int u) {
		List<Integer> succ = new ArrayList<Integer>();
		for(int v =0;v<this.matrix[u].length;v++){
			if(this.matrix[u][v]>0){
				succ.add(v);
			}
		}
		return succ;
	}

	/**
	 * @param v the vertex selected
	 * @return a list of vertices which are the predecessors of v
	 */
	public List<Integer> getPredecessors(int v) {
		List<Integer> pred = new ArrayList<Integer>();
		for(int u =0;u<this.matrix.length;u++){
			if(this.matrix[u][v]>0){
				pred.add(u);
			}
		}
		return pred;
	}
	
	
	// ------------------------------------------------
	// 					Methods 
	// ------------------------------------------------		
	
	/**
	 * @return true if the arc (from,to) exists in the graph.
 	 */
	public boolean isArc(int from, int to) {
		return this.matrix[from][to] > 0;
	}

	/**
	 * removes the arc (from,to) if there exists one between these nodes in the graph.
	 */
	public void removeArc(int from, int to) {
		if (this.isArc(from, to)) {
			this.matrix[from][to] = 0;
			this.nbArcs--;
		} else {
			System.out.println("There is no arc from " + from + " to " + to);
		}
	}

	/**
	 * Adds the arc (from,to). 
	 */
	public void addArc(int from, int to) {
		if (!this.isArc(from, to)) {
			this.matrix[from][to] = 1;
			this.nbArcs++;
		} else {
			System.out.println("There is already an arc from " + from + " to " + to);
		}
	}

	/**
	 * @return a new graph which is the inverse graph of this.matrix
 	 */
	public AdjacencyMatrixDirectedGraph computeInverse() {
		AdjacencyMatrixDirectedGraph amInv = new AdjacencyMatrixDirectedGraph(this.matrix);
		amInv.matrix = new int[this.nbNodes][this.nbNodes];
		for (int i = 0; i < this.nbNodes; i++) {
			for (int j = 0; j < this.nbNodes; j++) {
				if (this.matrix[i][j] > 0) {
					amInv.addArc(j, i);
				}
			}
		}
		return amInv;
	}

	@Override
	public String toString(){
		StringBuilder s = new StringBuilder("Adjacency Matrix: \n");
		for (int[] ints : matrix) {
			for (int anInt : ints) {
				s.append(anInt).append("\t");
			}
			s.append("\n");
		}
		s.append("\n");
		return s.toString();
	}

	public static void main(String[] args) {
		int[][] matrix2 = GraphTools.generateGraphData(10, 20, false, false, false, 100001);
		AdjacencyMatrixDirectedGraph am = new AdjacencyMatrixDirectedGraph(matrix2);
		System.out.println(am);
		System.out.println("n = "+am.getNbNodes()+ "\nm = "+am.getNbArcs() +"\n");
		
		// Successors of vertex 1 :
		System.out.println("Sucesssors of vertex 1 : ");
		List<Integer> t = am.getSuccessors(1);
		for (Integer integer : t) {
			System.out.print(integer + ", ");
		}
		
		// Predecessors of vertex 2 :
		System.out.println("\n\nPredecessors of vertex 2 : ");
		List<Integer> t2 = am.getPredecessors(2);
		for (Integer integer : t2) {
			System.out.print(integer + ", ");
		}

		// Is Edge from 0 to 3 :
		System.out.println("\n\nIs there an arc from 0 to 3 ? : " + am.isArc(0, 3));

		// We add an arc from 0 to 2 :
		System.out.println("\n\nWe add an arc from 0 to 2 : ");
		am.addArc(0, 2);
		System.out.println(am);
		System.out.println("\nIs there an arc from 0 to 2 ? : " + am.isArc(0, 2));

		// We remove the arc from 0 to 2 :
		System.out.println("\n\nWe remove the arc from 0 to 2 : ");
		am.removeArc(0, 2);
		System.out.println(am);
		System.out.println("\nIs there an arc from 0 to 2 ? : " + am.isArc(0, 2));

		// We reverse the graph :
		System.out.println("\n\nWe reverse the graph : ");
		System.out.println("The graph\n" + am);
		AdjacencyMatrixDirectedGraph amInv = am.computeInverse();
		System.out.println("The inverse graph\n" + amInv);

		am.displayGraphStream();
		amInv.displayGraphStream();
	}
}
