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

	public static List<DirectedNode> BFS(DirectedNode firstNodeToVisit) {
		List<DirectedNode> explored = new ArrayList<>();
		Queue<DirectedNode> queue = new LinkedList<>();
		queue.add(firstNodeToVisit);
		while (!queue.isEmpty()) {
			DirectedNode node = queue.poll();
			explored.add(node);
			List<Arc> arcSucc = node.getArcSucc();
			for (Arc arc : arcSucc) {
				DirectedNode secondNode = arc.getSecondNode();
				if (!explored.contains(secondNode) && !queue.contains(secondNode)) {
					queue.add(secondNode);
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

	public static List<UndirectedNode> BFS(UndirectedNode firstNodeToVisit) {
		List<UndirectedNode> explored = new ArrayList<>();
		Queue<UndirectedNode> queue = new LinkedList<>();
		queue.add(firstNodeToVisit);
		while (!queue.isEmpty()) {
			UndirectedNode node = queue.poll();
			explored.add(node);
			List<Edge> incidentEdges = node.getIncidentEdges();
			for (Edge incidentEdge : incidentEdges) {
				UndirectedNode nextNode = getNextNode(node, incidentEdge);
				if (!explored.contains(nextNode) && !queue.contains(nextNode)) {
					queue.add(nextNode);
				}
			}
		}
		return explored;
	}


	public static void main(String[] args) {
		int[][] Matrix = GraphTools.generateGraphData(10, 20, false, false, true, 100001);
		int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, true, true, false, 100001);
		GraphTools.afficherMatrix(Matrix);
		AdjacencyListDirectedGraph al = new AdjacencyListDirectedGraph(Matrix);
		AdjacencyListUndirectedValuedGraph alVal = new AdjacencyListUndirectedValuedGraph(matrixValued);
		System.out.println(al);


		DirectedNode node = new DirectedNode(7);
		DirectedNode first = al.getNodeOfList(node);
		List<DirectedNode> bfs = BFS(first);
		System.out.println(bfs);
		GraphTools.representationGraphique(Matrix);

		UndirectedNode nodeVal = new UndirectedNode(7);
		UndirectedNode firstUndirectedNode = alVal.getNodeOfList(nodeVal);
		List<UndirectedNode> bfsUndirectedNode = BFS(firstUndirectedNode);
		System.out.println(bfsUndirectedNode);
		GraphTools.representationGraphique(matrixValued);
	}
}
