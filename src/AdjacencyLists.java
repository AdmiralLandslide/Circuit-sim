import java.util.List;
import java.util.Stack;

/**
 * Created by Ruben on 22/02/2014.
 */
public class AdjacencyLists {
    int n;
    List<Integer>[] adj;
    AdjacencyLists(int n0) {
        n = n0;
        adj = (List<Integer>[])new List[n];
        for (int i = 0; i < n; i++)
            adj[i] = new Stack<Integer>();
    }
    void addEdge(int i, int j) {
        adj[i].add(j);
    }

}
