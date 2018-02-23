import java.util.*;

public class Node {
    boolean isBlocked;
    boolean isVisited;
    // Maximum of four neighbors
    List<Node> neighbors = new ArrayList<Node>();
    // The prior node that we came from in our path
    // Used for calculating 'f' 
    Node parent;
    // Heuristic for A* search
    double f, g, h;

    public Node() {
        this.isBlocked = false;
        this.isVisited = false;

        this.parent = null;
        f = Double.MAX_VALUE;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;
    }
}
