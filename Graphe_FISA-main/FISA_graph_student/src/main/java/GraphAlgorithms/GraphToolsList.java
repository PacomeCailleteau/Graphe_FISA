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
	private static int cpt;
	private static List<Integer> ordreFin = new ArrayList<>();

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
		pred[start.getLabel()] = null;

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
		debut = new int[n];
		fin = new int[n];
		cpt = 0;

		for (DirectedNode node : g.getNodes()) {
			if (visite[node.getLabel()] == 0) {
				explorerSommet(node);
			}
		}
		System.out.println("Ordre de fin : " + ordreFin);
	}

	private static void explorerSommet(DirectedNode node) {
		int label = node.getLabel();
		visite[label] = 1; // en cours de visite
		debut[label] = cpt++;
		System.out.println("Début de " + label + " : " + debut[label]);

		for (Arc arc : node.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			int voisinLabel = voisin.getLabel();
			if (visite[voisinLabel] == 0) { // pas encore visité
				explorerSommet(voisin);
			}
		}
		visite[label] = 2; // totalement visité
		fin[label] = cpt++;
		System.out.println("Fin de " + label + " : " + fin[label]);
		ordreFin.add(label);
	}

	public static void explorerGrapheBis(AdjacencyListDirectedGraph g) {
		int n = g.getNbNodes();
		visite = new int[n];
		System.out.println("\nComposantes fortement connexes :");

		Collections.reverse(ordreFin); // ordre décroissant des fins
		System.out.println("Ordre de fin inversé : " + ordreFin);

		for (int label : ordreFin) {
			if (visite[label] == 0) {
				System.out.print("CFC : ");
				explorerSommetBis(g.getNodeOfList(new DirectedNode(label)));
				System.out.println();
			}
		}
	}

	private static void explorerSommetBis(DirectedNode node) {
		int label = node.getLabel();
		visite[label] = 1;
		System.out.print(label + " ");

		for (Arc arc : node.getArcSucc()) {
			DirectedNode voisin = arc.getSecondNode();
			if (visite[voisin.getLabel()] == 0) {
				explorerSommetBis(voisin);
			}
		}

		visite[label] = 2;
	}

	public static Pair<List<Edge>, Integer> prim(AdjacencyListUndirectedValuedGraph g, UndirectedNode start) {
		int n = g.getNbNodes();
		boolean[] visited = new boolean[n];
		List<Edge> resultEdgeList = new ArrayList<>();
		Integer cost = 0;
		BinaryHeapEdge heap = new BinaryHeapEdge();

		int startLabel = start.getLabel();;
		visited[startLabel] = true;

		for (Edge e : start.getIncidentEdges()) {
			UndirectedNode to = e.getSecondNode();
			heap.insert(start, to, e.getWeight());
		}

		while (resultEdgeList.size() < n - 1 && !heap.isEmpty()) {
			Edge minEdge = heap.remove();
			int toLabel = minEdge.getSecondNode().getLabel();
			int fromLabel = minEdge.getFirstNode().getLabel();

			if (!visited[toLabel] || !visited[fromLabel]) {
				int newNodeLabel = visited[fromLabel] ? toLabel : fromLabel;
				visited[newNodeLabel] = true;
				resultEdgeList.add(minEdge);
				cost += minEdge.getWeight();

				UndirectedNode newNode = g.getNodes().get(newNodeLabel);
				for (Edge e : newNode.getIncidentEdges()) {
					UndirectedNode adj = e.getSecondNode();
					if (!visited[adj.getLabel()]) {
						heap.insert(newNode, adj, e.getWeight());
					}
				}
			}
		}
		return new Pair<>(resultEdgeList, cost);
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
		// GraphTools.representationGraphique(Matrix, true);

		List<UndirectedNode> bfsUndirectedNode = BFS(alVal);
		System.out.println("BFS Undirected nodes : " + bfsUndirectedNode);
		// GraphTools.representationGraphique(matrixValued, false);

		System.out.print("DFS récursif : ");
		explorerGraphe(al);
		GraphTools.representationGraphique(Matrix, true);
		System.out.println();

		System.out.print("Partie sur les composantes fortement connexes :\n");
		// DFS classique pour remplir ordreFin
		ordreFin = new ArrayList<>();
		explorerGraphe(al);

		// Inversion du graphe
		AdjacencyListDirectedGraph gInv = new AdjacencyListDirectedGraph(al.computeInverse());

		// Application du DFS sur le graphe inversé en suivant l'ordre de fin inversé
		explorerGrapheBis(gInv);
		System.out.println();

		System.out.println("Dijkstra : ");
        DirectedNode start = valuedDirectedGraph.getNodes().get(0);
        Pair<int[], DirectedNode[]> dijkstra = dijkstra(valuedDirectedGraph, start);
        int[] distances = dijkstra.getLeft();
        DirectedNode[] predecessors = dijkstra.getRight();
        System.out.println("Noms des sommets : " + Arrays.toString(valuedDirectedGraph.getNodes().stream().map(DirectedNode::getLabel).toArray()));
        System.out.println("Distance du sommet 0 : " + Arrays.toString(distances));
        System.out.println("Prédécesseurs : " + Arrays.toString(predecessors));
        System.out.println("Node's labels : " + Arrays.toString(valuedDirectedGraph.getNodes().stream().map(DirectedNode::getLabel).toArray()));
        System.out.println("Distances from node 0 : " + Arrays.toString(distances));
        System.out.println("Predecessors : " + Arrays.toString(predecessors));

        System.out.println("Prim : ");
		UndirectedNode startNode = alVal.getNodes().get(0);
		Pair<List<Edge>, Integer> prim = prim(alVal, startNode);
		List<Edge> edgeList = prim.getLeft();
		Integer cost = prim.getRight();
		System.out.println("Prim's algorithm result:");
		System.out.println("Edges: " + edgeList);
		System.out.println("Total cost: " + cost);

		GraphTools.representationGraphique(matrixValued, false);

	}
}
