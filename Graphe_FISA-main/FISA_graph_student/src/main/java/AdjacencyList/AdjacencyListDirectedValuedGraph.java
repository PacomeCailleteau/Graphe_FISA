package AdjacencyList;

import GraphAlgorithms.GraphTools;
import Nodes_Edges.Arc;
import Nodes_Edges.DirectedNode;

import java.util.List;

public class AdjacencyListDirectedValuedGraph extends AdjacencyListDirectedGraph {

	//--------------------------------------------------
    // 				Constructors
    //--------------------------------------------------

	public AdjacencyListDirectedValuedGraph(int[][] matrixVal) {
    	super();
    	this.nbNodes = matrixVal.length;
        for (int i = 0; i < this.nbNodes; i++) {
            this.nodes.add(new DirectedNode(i));
        }
        for (DirectedNode n1 : this.getNodes()) {
            for (int j = 0; j < matrixVal[n1.getLabel()].length; j++) {
            	DirectedNode n2 = this.getNodes().get(j);
                if (matrixVal[n1.getLabel()][j] != 0) {
                	Arc a1 = new Arc(n1,n2,matrixVal[n1.getLabel()][j]);
                    n1.addArc(a1);
                    this.arcs.add(a1);
                	n2.addArc(a1);
                    this.nbArcs ++;
                }
            }
        }            	
    }

    // ------------------------------------------
    // 				Accessors
    // ------------------------------------------
    

    /**
     * Adds the arc (from,to) with cost if it is not already present in the graph. 
     * And adds this arc to the incident list of both extremities (nodes) and into the global list "arcs" of the graph.
     */
    public void addArc(DirectedNode from, DirectedNode to, int cost) {
        if (!isArc(from, to)) {
            Arc a = new Arc(from, to, cost);
            this.arcs.add(a);
            this.getNodeOfList(from).addArc(a);
            this.getNodeOfList(to).addArc(a);
        }
    }

    public Integer getWeight(DirectedNode from, DirectedNode to) {
        if (isArc(from, to)) {
            List<Arc> arcSucc = this.getNodeOfList(from).getArcSucc();
            for (Arc a : arcSucc) {
                if (a.getSecondNode().equals(to)) {
                    return a.getWeight();
                }
            }
        }
        System.out.println("The arc (" + from.getLabel() + "," + to.getLabel() + ") is not in the graph.");
        return null;
    }

    public static void main(String[] args) {
        int[][] matrixValued = GraphTools.generateValuedGraphData(10, false, false, true, false, 100001);
        GraphTools.afficherMatrix(matrixValued);
        AdjacencyListDirectedValuedGraph al = new AdjacencyListDirectedValuedGraph(matrixValued);
        System.out.println("Seed used to have the following results : 100001");
        System.out.println(al);
        DirectedNode n_3 = new DirectedNode(3);
        DirectedNode n_7 = new DirectedNode(7);
        System.out.println("Should be true : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        Integer weight = al.getWeight(n_7, n_3);
        System.out.println("Value should be 3 : " + weight);
        System.out.println("Should be false : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));

        al.removeArc(n_3, n_7);
        System.out.println("Should be true : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be false : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));

        al.removeArc(n_7, n_3);
        System.out.println("Should be false : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be false : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));

        al.addArc(n_3, n_7, 22);
        System.out.println("Should be false : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be true : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));
        weight = al.getWeight(n_3, n_7);
        System.out.println("Value should be 22 : " + weight);

        al.addArc(n_3, n_7, 10);
        System.out.println("Should be false : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be true : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));
        weight = al.getWeight(n_3, n_7);
        System.out.println("Value should be 22 : " + weight);

        al.addArc(n_7, n_3, 17);
        System.out.println("Should be true : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be true : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));
        weight = al.getWeight(n_7, n_3);
        System.out.println("Value should be 17 : " + weight);

        al.removeArc(n_3, n_7);
        System.out.println("Should be true : (n_7,n_3) is it in the graph ? " +  al.isArc(n_7, n_3));
        System.out.println("Should be false : (n_3,n_7) is it in the graph ? " +  al.isArc(n_3, n_7));

    }
	
}
