package GraphAlgorithms;

import java.util.*;

import AdjacencyList.AdjacencyListDirectedGraph;
import AdjacencyList.AdjacencyListDirectedValuedGraph;
import AdjacencyList.AdjacencyListUndirectedValuedGraph;
import Nodes_Edges.*;
import Collection.Pair;

public class GraphToolsList  extends GraphTools {

	public static final int MAX_INT_DIV_2 = Integer.MAX_VALUE / 2;
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

	public static Pair<int[], DirectedNode[]> dijkstra(AdjacencyListDirectedGraph g, DirectedNode start) {
		int n = g.getNbNodes();
		boolean[] mark = new boolean[n];
		int[] val = new int[n];
		DirectedNode[] pred = new DirectedNode[n];

		for (int i = 0; i < n; i++) {
			mark[i] = false;
			val[i] = Integer.MAX_VALUE / 2;
			pred[i] = null;
		}

		val[start.getLabel()] = 0;
		pred[start.getLabel()] = start;

		int markedCount = 0;

		while (markedCount < n) {
			int x = -1;
			int min = Integer.MAX_VALUE / 2;

			for (int i = 0; i < n; i++) {
				if (!mark[i] && val[i] < min) {
					x = i;
					min = val[i];
				}
			}

			if (x == -1) {
				break;
			}

			mark[x] = true;
			markedCount++;

			DirectedNode current = g.getNodes().get(x);

			for (Arc arc : current.getArcSucc()) {
				DirectedNode neighbor = arc.getSecondNode();
				int y = neighbor.getLabel();
				if (!mark[y] && val[x] + arc.getWeight() < val[y]) {
					val[y] = val[x] + arc.getWeight();
					pred[y] = current;
				}
			}
		}

		return new Pair<>(val, pred);
	}

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
		AdjacencyListDirectedGraph valuedDirectedGraph = new AdjacencyListDirectedValuedGraph(Matrix);
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

		DirectedNode start = valuedDirectedGraph.getNodes().get(0);
		Pair<int[], DirectedNode[]> dijkstra = dijkstra(valuedDirectedGraph, start);
		int[] distances = dijkstra.getLeft();
		DirectedNode[] predecessors = dijkstra.getRight();
		System.out.println("Node's labels : " + Arrays.toString(valuedDirectedGraph.getNodes().stream().map(DirectedNode::getLabel).toArray()));
		System.out.println("Distances from node 0 : " + Arrays.toString(distances));
		System.out.println("Predecessors : " + Arrays.toString(predecessors));
	}
}
