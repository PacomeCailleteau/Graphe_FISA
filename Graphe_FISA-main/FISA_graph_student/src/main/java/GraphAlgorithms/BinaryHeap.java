package GraphAlgorithms;


public class BinaryHeap {

    private int[] nodes;
    private int pos; // pos correspond au dernier indice vide (là où il y a un MAX INT)

    public BinaryHeap() {
        this.nodes = new int[32];
        for (int i = 0; i < nodes.length; i++) {
            this.nodes[i] = Integer.MAX_VALUE;
        }
        this.pos = 0;
    }

    public void resize() {
        int[] tab = new int[this.nodes.length + 32];
        for (int i = 0; i < nodes.length; i++) {
            tab[i] = Integer.MAX_VALUE;
        }
        System.arraycopy(this.nodes, 0, tab, 0, this.nodes.length);
        this.nodes = tab;
    }

    public boolean isEmpty() {
        return pos == 0;
    }

    public int getParentPos(int pos) {
        return (pos-1)/2;
    }

    public int getParent(int pos) {
        return nodes[getParentPos(pos)];
    }

    public void insert(int element) {
        if (pos+1 >= nodes.length) {
            resize();
        }
    	nodes[pos] = element;
        pos++;

        remonterElement(element);
    }

    private void remonterElement(int element) {
        int currentElementPos = pos-1;
        int currentParent = getParent(currentElementPos);
        int currentParentPos = getParentPos(currentElementPos);

        while (currentParent > element && currentElementPos > 0) {
            swap(currentParentPos, currentElementPos);

            currentElementPos = currentParentPos;
            currentParent = getParent(currentElementPos);
            currentParentPos = getParentPos(currentElementPos);
        }
    }

    private void descendreElement(int element) {
        int currentElementPos = 0;
        int bestChildPos = getBestChildPos(currentElementPos);

        while (bestChildPos < pos && element > nodes[bestChildPos]) {
            swap(currentElementPos, bestChildPos);

            currentElementPos = bestChildPos;
            bestChildPos = getBestChildPos(currentElementPos);
        }
    }

    public int remove() {
        if (isEmpty()) {
            throw new IllegalStateException("Le tas binaire est vide, il n'y a rien à supprimer.");
        }
        int removedValue = nodes[0];
    	swap(0, pos-1);
        nodes[pos-1] = Integer.MAX_VALUE;
        pos--;

        if (!isEmpty()) {
            descendreElement(nodes[0]);
        }

    	return removedValue;
    }

    private int getBestChildPos(int src) {
        if (isLeaf(src)) { // the leaf is a stopping case, then we return a default value
            return Integer.MAX_VALUE;
        } else if (2 * src + 1 == pos - 1) {
            return 2*src+1;
        } else {
            int leftChild = nodes[2 * src + 1];
            int rightChild = nodes[2 * src + 2];
            if (leftChild > rightChild) {
                return 2*src+2;
            }
            return 2*src+1;
        }
    }

    
    /**
	 * Test if the node is a leaf in the binary heap
	 * 
	 * @return true if it's a leaf or false else
	 * 
	 */	
    private boolean isLeaf(int src) {
    	return src*2+1 >= pos;
    }

    private void swap(int father, int child) {
        int temp = nodes[father];
        nodes[father] = nodes[child];
        nodes[child] = temp;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < pos; i++) {
            s.append(nodes[i]).append(", ");
        }
        return s.toString();
    }

    /**
	 * Recursive test to check the validity of the binary heap
	 * 
	 * @return a boolean equal to True if the binary tree is compact from left to right
	 * 
	 */
    public boolean test() {
        return this.isEmpty() || testRec(0);
    }

    private boolean testRec(int root) {
        if (isLeaf(root)) {
            return true;
        } else {
            int left = 2 * root + 1;
            int right = 2 * root + 2;
            if (right >= pos) {
                return nodes[left] >= nodes[root] && testRec(left);
            } else {
                return nodes[left] >= nodes[root] && testRec(left) && nodes[right] >= nodes[root] && testRec(right);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println((1/2));
        BinaryHeap jarjarBin = new BinaryHeap();
        System.out.println(jarjarBin.isEmpty()+"\n");
        int k = 20;
        int m = k;
        int min = 2;
        int max = 20;
        while (k > 0) {
            int rand = min + (int) (Math.random() * ((max - min) + 1));
            System.out.println("Ajout : " + rand);
            jarjarBin.insert(rand);            
            k--;
        }

        System.out.println("-------------------------------");
        System.out.println(jarjarBin);
        jarjarBin.remove();
        jarjarBin.remove();
        System.out.println(jarjarBin);

        GraphTools.drawBinaryHeap(jarjarBin.nodes);

        System.out.println(jarjarBin.test());
        System.out.println(jarjarBin.testRec(0));
    }

}
