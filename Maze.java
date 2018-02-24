import java.util.*;
import java.util.Random;

public class Maze {
    private int length;
    private int width;
    public Node Start;
    public Node End;
    public Node[][] board;
    public Node Current;
    private boolean[][] visited;


    public Maze(int length,int width){
        this.length = length;
        this.width = width;
        this.board = new Node[length+2][width+2];
        this.Current = null;
        for(int i=0;i< length+2;i++){
            for(int j=0;j < width+2;j++){
                this.board[i][j] = new Node(i,j);
            }
        }

        init();
        generate();
        this.Start = this.board[1][1];
        this.Current = this.Start;
        // TODO play around with where to put the End node
        this.End = this.board[length-3][width-3];
        setNeighbors();
    }

    public void init(){
        // initialize border cells as already visited
        visited = new boolean[length+2][width+2];
        for( int i = 0; i < length+2 ; i++){
            visited[i][0] = true;
            visited[i][length+1] = true;
        }
        for ( int i = 0; i < width+2; i++){
            visited[0][i] = true;
            visited[width+1][i] = true;
        }

        // initialze all walls as present
        for(int i=0;i< length+2;i++){
            for(int j=0;j< width+2;j++){
                this.board[i][j].northwall = true;
                this.board[i][j].eastwall = true;
                this.board[i][j].southwall = true;
                this.board[i][j].westwall = true;
            }
        }

    }

    // Set the connection nodes for each node
    private void setNeighbors()
    {
        for (int x = 1; x < length-1; ++x)
        {
            for (int y = 1; y < width-1; ++y)
            {
                // North
                if (!this.board[x][y].northwall)
                {
                    this.board[x][y].neighbors.add(this.board[x][y-1]);
                }
                // South
                if (!this.board[x][y].southwall)
                {
                    this.board[x][y].neighbors.add(this.board[x][y+1]);
                }
                // East
                if (!this.board[x][y].eastwall)
                {
                    this.board[x][y].neighbors.add(this.board[x+1][y]);
                }
                // West
                if (!this.board[x][y].westwall)
                {
                    this.board[x][y].neighbors.add(this.board[x-1][y]);
                }
            }
        }
    }

    // Generate maze using DFS starting at position x, y
    public void GenerateMaze(int x, int y){
        visited[x][y] = true;

        while(!visited[x][y+1]|| !visited[x+1][y] || !visited[x][y-1] || !visited[x-1][y]) {

            while(true){
                Random rand = new Random();
                int n = rand.nextInt(4);
                if ( n == 0 && !visited[x][y+1]){
                    this.board[x][y].southwall = false;
                    this.board[x][y+1].northwall = false;
                    GenerateMaze(x, y+1);
                    break;
                }
                else if ( n == 1 && !visited[x+1][y]){
                    this.board[x][y].eastwall = false;
                    this.board[x+1][y].westwall = false;
                    GenerateMaze(x+1, y);
                    break;
                }
                else if ( n == 2 && !visited[x][y-1]){
                    this.board[x][y].northwall = false;
                    this.board[x][y-1].southwall = false;
                    GenerateMaze(x, y-1);
                    break;
                }
                else if( n == 3 && !visited[x-1][y]){
                    this.board[x][y].westwall = false;
                    this.board[x-1][y].eastwall = false;
                    GenerateMaze(x-1, y);
                    break;
                }
            }
        }
    }

    public void generate(){
        GenerateMaze(1,1);
    }


    //========================================
    // A* functions
    // =======================================

    public boolean isDestination(Node node)
    {
        if(node == End)
            return true;
        else   
            return false;
    }

    public double calculateHManhattan(int row, int col)
    {
        return Math.abs(row - End.x) + Math.abs(col - End.y);
    }

    public double calculateHValue(int row, int col)
    {
        return ((double) Math.sqrt((row-(length-1)*(row-(length-1)) + (col-(width-1)*(col-(width-1))))));
    }

    // If a path is found, trace which nodes were the optimal path
    private void tracePath(Node lastVisitedNode)
    {
        Node pathPtr = lastVisitedNode;
        while (pathPtr != Start)
        {
            pathPtr.isVisited = true;
            pathPtr = pathPtr.parent;
        }
    }

    public void aStarSearch()
    {
        boolean hasPath = false;

        Set<Node> openSet = new LinkedHashSet();
        // keeps track of the nodes we've checked to be the best 'f' value
        boolean[][] closedSet = new boolean[length][width];

        // Add the Starting node to the openset
        Start.g = 0;
        Start.h = 0;
        Start.f = 0;
        openSet.add(Start);

        Node lowestFNode = null;
        while(!openSet.isEmpty())
        {
            lowestFNode = getLowestNode(openSet);

            // pop the lowestFNode from the open set
            // while adding it to the closedSet.
            // This indicates that the node was found to be the best option
            openSet.remove(lowestFNode);
            closedSet[lowestFNode.x][lowestFNode.y] = true;

            // Generate the the neighbors and recalculate the f(n) = g(n) + h(n)
            double newG, newF, newH;
            for (int i = 0; i < lowestFNode.neighbors.size(); ++i)
            {
                Node currentNeighbor = lowestFNode.neighbors.get(i);
                int cx = currentNeighbor.x;
                int cy = currentNeighbor.y;
                // Check that the neighbor is the destination Node
                if (isDestination(currentNeighbor))
                {
                    // If it is, then set the parent node
                    currentNeighbor.parent = lowestFNode;
                    hasPath = true;
                }
                // Check if the neighbor is on the closed list
                // or if it's blocked 
                else if (closedSet[cx][cy] == false)
                {
                    // Recalculate the neighbor's 'f'
                    newG = lowestFNode.g + 1.0; 
                    newH = calculateHManhattan(cx, cy);
                    newF = newG + newH;

                    // Add the current neighbor to the open list
                    // since it's a candidate to be checked for the lowest 'f'
                    currentNeighbor.g = newG;
                    currentNeighbor.h = newH;
                    currentNeighbor.f = newF;
                    currentNeighbor.parent = lowestFNode;

                    // Since we're using a set, there's no need to check if 
                    // that node has already been inserted.
                    openSet.add(currentNeighbor);

                }
            }
        }

        // Trace that path based on the last visited node
        // If a viable path is found, then this should start at End
        tracePath(lowestFNode);

        // No viable path available
        if (hasPath)
            System.out.println("Found a viable path from Start to End");        
        else
            System.out.println("No viable path found");        
       
    }

    // Returns the node with the lowest 'f' within the open set
    private Node getLowestNode(Set<Node> openSet)
    {
        // find the node with the least f
        double minF = Double.MAX_VALUE;
        Node[] openArray = openSet.toArray(new Node[0]);

        Node q = null;
        for (int i = 0; i < openArray.length; ++i)
        {
            if (openArray[i].f < minF)    
            {
                minF = openArray[i].f;
                q = openArray[i];
            }
        }
        return q;
    }



    public void print(){
        for (int i=1; i< width+1;i++){
            for(int j=1; j < length+1;j++){
                if(this.board[j][i].northwall){
                    System.out.print("+---");
                }
                else if (this.board[j][i].isVisited) {
                    System.out.print("+ " + "\033[31;1mV\033[0m" + " ");
                }
                else {
                    System.out.print("+   ");
                }
            }
            System.out.println("+");

            for(int j=1;j < length+1;j++){
                if(this.board[j][i].westwall){
                    if(this.board[j][i] == Start){
                        System.out.print("| A ");
                    }
                    else if (this.board[j][i] == End) {
                        System.out.print("| E ");
                    }
                    /*
                    else if (this.board[j][i].isVisited) {
                        //System.out.print("| V ");
                        System.out.print("| \033[31;1mV\033[0m ");
                    }
                    */
                    else {
                        System.out.print("|   ");
                    }
                }
                else {
                    if(this.board[j][i] == Start){
                        System.out.print("| A ");
                    }
                    else if (this.board[j][i] == End) {
                        System.out.print("  E ");
                    }
                    else if (this.board[j][i].isVisited) {
                        //System.out.print("| V ");
                        System.out.print(" \033[31;1mV\033[0m  ");
                    }
                    else {
                        System.out.print("    ");
                    }
                }
                /*if(this.board[j][i].eastwall){
                  System.out.print("  |");
                }
                else {
                System.out.print("   ");
                }*/
            }
            System.out.println("|");
            if(i == width){
                for(int j=1; j < length+1;j++){
                    if(this.board[j][i].southwall){
                        System.out.print("+---");
                    }
                    else if (this.board[j][i].isVisited) {
                        System.out.print("+ " + "\033[31;1mV\033[0m" + " ");
                    }
                    else {
                        System.out.print("+   ");
                    }
                }
                System.out.println("+");

            }
        }
    }

    // Method for debugging purposes
    // Counts and returns the number of 
    // adjecent connections for a particular node
    static int displayConnections(Node node)
    {
        return node.neighbors.size();
    }

}
