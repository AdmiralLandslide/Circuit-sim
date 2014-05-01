import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


/**
* Created by Ruben on 22/02/2014.
*/
public class AdjacencyLists implements Cloneable{
    int n;
    Stack<Integer>[] adj;

    AdjacencyLists(int n0) {
        n = n0;
        adj = (Stack<Integer>[])new Stack[n];
        for (int i = 0; i < n; i++)
            adj[i] = new Stack<Integer>();
    }

    AdjacencyLists(AdjacencyLists toCopy) {
        n = toCopy.getN();
        adj = (Stack<Integer>[])new Stack[n];
        for (int i = 0; i < n; i++)
            adj[i] = new Stack<Integer>();
        int i = 0;
        while (toCopy.getAdjacentNodes(i).size() > 0) {
            for (int j=0; j<toCopy.stackSize(i); j++) {
                this.addEdge(i, toCopy.getNode(i,j));
            }
            i++;
        }
    }

    public int getN() { return n; }

    public void addEdge(int i, int j) {
//        System.out.println(i + " " + j);
        if (!adj[i].contains(j)) {
            adj[i].add(j);
        }
    }

    public void printList() {
        System.out.println("print adjacency list size here : " + Canvas.coordListSize);
        for (int i=0; i<Canvas.coordListSize; i++) {
            System.out.print(i + "   ");
            for (int j=0; j<adj[i].size(); j++) {
                System.out.print(adj[i].get(j) + " ");
            }
            System.out.println();
//            System.out.print(adj[i].get(0));
        }
    }
    public int mostConnections() {
        int x = 0;
        int biggest = 0;
        for (int i = 0; i< n; i++) {
            if (adj[i].size() > x ) {
                x = adj[i].size();
                biggest = i;
            }
        }
    return biggest;
    }

    public int getNode(int i, int j) {
        try {
            int x = adj[i].get(j);
            return x;
        }
        catch (ArrayIndexOutOfBoundsException n) {
            Main.errorLabel.setText("Circuit is incomplete");
            return -1;
        }

    }

    public int stackSize(int i) {
        int x = adj[i].size();
        return x;
    }
    public boolean contains(int index, int val) {
        if (adj[index].contains(val)) { return true;}
        else return false;
    }

    public List<Integer> getAdjacentNodes(int i) {
        return adj[i];
    }

    public int sizeOfAdj() {
        boolean end = false;
        int i = 0;
        while (!end ) {
            if (adj[i].size() == 0) {
                end = true;
            }
            else {i++;}
        }
        return i;
    }

    public int countEdges() {
        boolean end = false;
        int size = 0;
        int i=0;
        while (adj[i].size() != 0) {
            size += adj[i].size();
            i++;
        }
        size /= 2;
        return size;
    }

    public void removeEdge(int i, int j) {
        adj[i].remove(Integer.valueOf(j));
        adj[j].remove(Integer.valueOf(i));
    }

    public boolean removeDeadEnds() {
        boolean removed = false;
        for (int i=0; i< Canvas.coordListSize;i++) {
            if (adj[i].size() == 1) {
                removed = true;
                adj[i].pop();
                for (int j=0; j<Canvas.coordListSize; j++) {
                    if (adj[j].contains(i)) {
                        adj[j].remove(Integer.valueOf(i));
                    }
                }
            }
        }
        return removed;
    }
}
