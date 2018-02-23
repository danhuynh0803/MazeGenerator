import java.util.*;

public class Node {
    boolean isBlocked;
    boolean isVisited;
    Node north;
    Node south;
    Node east;
    Node west;

    public Node() {
        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
        this.isBlocked = false;
        this.isVisited = false;
    }
}
