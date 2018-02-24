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
    int x, y;

    public Node(int x, int y) {
        this.isBlocked = false;
        this.isVisited = false;

        this.parent = null;
        f = Double.MAX_VALUE;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;

        // Position of the node on the board
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, Node parent) {
        this.isBlocked = false;
        this.isVisited = false;

        this.parent = parent;
        f = Double.MAX_VALUE;
        g = Double.MAX_VALUE;
        h = Double.MAX_VALUE;

        // Position of the node on the board
        this.x = x;
        this.y = y;
    }
}
