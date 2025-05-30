package GraphAlgorithms;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Collection.Triple;
import Nodes_Edges.*;

public class GraphToolsList  extends GraphTools {

	private static int _DEBBUG =0;

	private static int[] visite;
	private static int[] debut;
	private static int[] fin;
	private static List<Integer> order_CC;
	private static int cpt=0;

	//--------------------------------------------------
	// 				Constructors
	//--------------------------------------------------

	public GraphToolsList(){
		super();
	}

	// ------------------------------------------
	// 				Accessors
	// ------------------------------------------



	// ------------------------------------------
	// 				Methods
	// ------------------------------------------

	public static List<DirectedNode> BFS(AdjacencyListDirectedGraph g) {
		List<DirectedNode> nodes = g.getNodes();
		List<DirectedNode> explored = new ArrayList<>();
		Queue<DirectedNode> fifo = new LinkedList<>();
		for (DirectedNode directedNode : nodes) { // Pour récupérer aussi les sommets qui sont des sources ou ceux qui sont isolés
			if (!explored.contains(directedNode)) {
				fifo.add(directedNode);
			}
			while (!fifo.isEmpty()) {
				DirectedNode node = fifo.poll();
				explored.add(node);
				List<Arc> arcSucc = node.getArcSucc();
				for (Arc arc : arcSucc) {
					DirectedNode secondNode = arc.getSecondNode();
					if (!explored.contains(secondNode) && !fifo.contains(secondNode)) {
						fifo.add(secondNode);
					}
				}
			}
		}
		return explored;
	}

	private static UndirectedNode getNextNode(UndirectedNode node, Edge edge) {
		UndirectedNode firstNode = edge.getFirstNode();
		if (Objects.equals(firstNode, node)) {
			return edge.getSecondNode();
		}
		return firstNode;
	}

	public static List<UndirectedNode> BFS(AdjacencyListUndirectedValuedGraph g) {
		List<UndirectedNode> explored = new ArrayList<>();
		Queue<UndirectedNode> fifo = new LinkedList<>();
		for (UndirectedNode undirectedNode : g.getNodes()) {
			if (!explored.contains(undirectedNode)) {
				fifo.add(undirectedNode);
			}
			while (!fifo.isEmpty()) {
				UndirectedNode node = fifo.poll();
				explored.add(node);
				List<Edge> incidentEdges = node.getIncidentEdges();
				for (Edge incidentEdge : incidentEdges) {
					UndirectedNode nextNode = getNextNode(node, incidentEdge);
					if (!explored.contains(nextNode) && !fifo.contains(nextNode)) {
						fifo.add(nextNode);
					}
				}
			}
		}
		return explored;
	}

	public static void explorerGraphe(AdjacencyListDirectedGraph g) {
		int n = g.getNbNodes();
		visite = new int[n];

		for (DirectedNode node : g.getNodes()) {
			if (visite[node.getLabel()] == 0) {
				explorerSommet(node);
			}
		}
	}

	private static void explorerSommet(DirectedNode node) {
		int label = node.getLabel();
		visite[label] = 1;
		System.out.print(label + " ");

		for (Arc arc : node.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			if (visite[voisin.getLabel()] == 0) {
				explorerSommet(voisin);
			}
		}
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		AdjacencyListUndirectedValuedGraph alVal = new AdjacencyListUndirectedValuedGraph(matrixValued);
		System.out.println(al);


		List<DirectedNode> bfs = BFS(al);
		System.out.println("BFS Directed nodes : " + bfs);
		GraphTools.representationGraphique(Matrix, true);

		List<UndirectedNode> bfsUndirectedNode = BFS(alVal);
		System.out.println("BFS Undirected nodes : " + bfsUndirectedNode);
		GraphTools.representationGraphique(matrixValued, false);

		System.out.print("DFS récursif : ");
		explorerGraphe(al);
		System.out.println();
	}
}
