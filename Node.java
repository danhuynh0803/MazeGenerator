import java.util.*;

public class Node {
    public boolean isBlocked;
    private boolean isVisited;
    public Node north;
    public Node south;
    public Node east;
    public Node west;
    private int l; //length
    private int w; //width


    public Node(int l, int w,double p){
        this.l = l;
        this.w = w;
        this.north = null;
        this.south = null;
        this.east = null;
        this.west = null;
        this.isBlocked = isitblocked(p);
        this.isVisited = false;
    }

    private Boolean isitblocked (double p){
        if( Math.random() >= p)
            return false;
        else
            return true;
    }
}

