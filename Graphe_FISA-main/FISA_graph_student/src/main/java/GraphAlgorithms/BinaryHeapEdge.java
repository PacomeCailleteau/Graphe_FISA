package GraphAlgorithms;

import java.util.ArrayList;
import java.util.List;

import Nodes_Edges.DirectedNode;
import Nodes_Edges.Edge;
import Nodes_Edges.UndirectedNode;

public class BinaryHeapEdge {

	/**
	 * A list structure for a faster management of the heap by indexing
	 * 
	 */
	private  List<Edge> binh;

    public BinaryHeapEdge() {
        this.binh = new ArrayList<Edge>();
    }

    public boolean isEmpty() {
        return binh.isEmpty();
    }

    /**
	 * Insert a new edge in the binary heap
	 * 
	 * @param from one node of the edge
	 * @param to one node of the edge
	 * @param val the edge weight
	 */
    public void insert(UndirectedNode from, UndirectedNode to, int val) {
    	Edge edge = new Edge(from, to, val);
		binh.add(edge);

		remonterElement(edge);
    }

	private void remonterElement(Edge edge) {
		int weight = edge.getWeight();
		int currentElementPos = binh.size()-1;
		Edge currentParent = getParent(currentElementPos);
		int currentParentPos = getParentPos(currentElementPos);

        while (currentParent.getWeight() > weight && currentElementPos > 0) {
            swap(currentParentPos, currentElementPos);

            currentElementPos = currentParentPos;
            currentParent = getParent(currentElementPos);
            currentParentPos = getParentPos(currentElementPos);
        }
	}

	public int getParentPos(int pos) {
		return (pos-1)/2;
	}

	public Edge getParent(int pos) {
		return binh.get(getParentPos(pos));
	}

	private void descendreElement(Edge edge) {
		int weight = edge.getWeight();
		int currentElementPos = 0;
		int bestChildPos = getBestChildPos(currentElementPos);

		while (bestChildPos < binh.size() && weight > binh.get(bestChildPos).getWeight()) {
			swap(currentElementPos, bestChildPos);

			currentElementPos = bestChildPos;
			bestChildPos = getBestChildPos(currentElementPos);
		}
	}



	/**
	 * Removes the root edge in the binary heap, and swap the edges to keep a valid binary heap
	 * 
	 * @return the edge with the minimal value (root of the binary heap)
	 * 
	 */
    public Edge remove() {
		if (isEmpty()) {
			throw new IllegalStateException("The binary heap is empty, nothing to remove.");
		}
		Edge first = binh.get(0);
		Edge removedEdge = new Edge(first.getFirstNode(), first.getSecondNode(), first.getWeight());
		int lastIndex = binh.size() - 1;
		swap(0, lastIndex);
		binh.remove(lastIndex);

		if (!binh.isEmpty()) {
			descendreElement(binh.get(0));
		}

		return removedEdge;
        
    }
    

    /**
	 * From an edge indexed by src, find the child having the least weight and return it
	 * 
	 * @param src an index of the list edges
	 * @return the index of the child edge with the least weight
	 */
    private int getBestChildPos(int src) {
    	int lastIndex = binh.size()-1; 
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else if (lastIndex == 2 * src + 1) {
			return 2 * src+1;
		}
		else {
			Edge leftChild = binh.get(2 * src + 1);
			Edge rightChild = binh.get(2 * src + 2);
			if (leftChild.getWeight() > rightChild.getWeight()) {
				return 2*src+2;
			}
			return 2*src+1;
        }
    }

    private boolean isLeaf(int src) {
    	return src*2+1 >= binh.size();
    }

	public int[] toIntHeap() {
		int[] res = new int[binh.size()];
		for (int i = 0; i < binh.size(); i++) {
			res[i] = binh.get(i).getWeight();
		}
		return res;
	}

    
    /**
	 * Swap two edges in the binary heap
	 * 
	 * @param father an index of the list edges
	 * @param child an index of the list edges
	 */
    private void swap(int father, int child) {
		Edge temp = binh.get(father);
		binh.set(father, binh.get(child));
		binh.set(child, temp);

    }

    
    /**
	 * Create the string of the visualisation of a binary heap
	 * 
	 * @return the string of the binary heap
	 */
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Edge no: binh) {
            s.append(no).append(", ");
        }
        return s.toString();
    }
    
    
    private String space(int x) {
		StringBuilder res = new StringBuilder();
		for (int i=0; i<x; i++) {
			res.append(" ");
		}
		return res.toString();
	}
	
	/**
	 * Print a nice visualisation of the binary heap as a hierarchy tree
	 * 
	 */	
	public void lovelyPrinting(){
		int nodeWidth = this.binh.get(0).toString().length();
		int depth = 1+(int)(Math.log(this.binh.size())/Math.log(2));
		int index=0;
		
		for(int h = 1; h<=depth; h++){
			int left = ((int) (Math.pow(2, depth-h-1)))*nodeWidth - nodeWidth/2;
			int between = ((int) (Math.pow(2, depth-h))-1)*nodeWidth;
			int i =0;
			System.out.print(space(left));
			while(i<Math.pow(2, h-1) && index<binh.size()){
				System.out.print(binh.get(index) + space(between));
				index++;
				i++;
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	// ------------------------------------
    // 					TEST
	// ------------------------------------

	/**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    private boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
    	System.out.println("root= "+root);
    	int lastIndex = binh.size()-1; 
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            System.out.println("left = "+left);
            System.out.println("right = "+right);
            if (right >= lastIndex) {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left);
            } else {
                return binh.get(left).getWeight() >= binh.get(root).getWeight() && testRec(left)
                    && binh.get(right).getWeight() >= binh.get(root).getWeight() && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        BinaryHeapEdge jarjarBin = new BinaryHeapEdge();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));                        
            System.out.println("insert : " + rand);
            jarjarBin.insert(new UndirectedNode(k), new UndirectedNode(k+30), rand);
            k--;
        }

		System.out.println("-------------------------------");
		System.out.println(jarjarBin);
		jarjarBin.remove();
		jarjarBin.remove();
		System.out.println(jarjarBin);

		GraphTools.drawBinaryHeap(jarjarBin.toIntHeap());

        System.out.println(jarjarBin.test());
        System.out.println(jarjarBin.testRec(0));
	}

}

