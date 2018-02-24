import java.util.*;

 public class Maze {
    public int length;
    public int width;
    public Node Start;
    public Node End;
    public Node[][] board;

    public Maze(int length, int width, double p){
        this.length = length;
        this.width = width;
        this.board = new Node[length][width];

        for(int i=0;i< length;i++){
            for(int j=0;j < width;j++){
                this.board[i][j] = new Node(i, j);
                // Set the node as being a wall
                // depending on the probability 'p' that is passed in
                if (Math.random() < p)
                {
                    this.board[i][j].isBlocked = true;
                }
            }
        }

        // Set Start of maze and the End of maze
        // By default these two nodes should be unblocked
        this.Start = this.board[0][0];
        this.Start.f = 0;
        this.Start.g = 0;
        this.Start.h = 0;

        this.End = this.board[length-1][width-1];
        this.Start.isBlocked = false;
        this.End.isBlocked = false;

        // Set the connections for each node as long as the adjacent node is not blocked
        // A node can have a max of four adjacent connections:
        // North, South, East, West
        for(int i=0;i< length;i++){
            for(int j=0;j< width;j++){
                // Set West
                if( i>0 && !this.board[i-1][j].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i-1][j]);
                }
                // Set East
                if( i< this.length-1 && !this.board[i+1][j].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i+1][j]);
                }
                // Set South
                if( j< this.width-1 && !this.board[i][j+1].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i][j+1]);
                }
                // Set North
                if (j > 0 && !this.board[i][j-1].isBlocked){
                    this.board[i][j].neighbors.add(this.board[i][j-1]);
                }
            }
        }
    }

    public boolean isDestination(Node node)
    {
        if(node == End)
            return true;
        else   
            return false;
    }

    public double calculateHManhattan(int row, int col)
    {
        return Math.abs(row - (length-1)) + Math.abs(col - (width-1));
    }

    public double calculateHValue(int row, int col)
    {
        return ((double) Math.sqrt((row-(length-1)*(row-(length-1)) + (col-(width-1)*(col-(width-1))))));
    }

    // If a path is found, trace which nodes were the optimal path
    public void tracePath()
    {
        Node pathPtr = End;
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
        Node lowestFNode = null;
        openSet.add(Start);

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
                    tracePath();
                }
                // Check if the neighbor is on the closed list
                // or if it's blocked 
                else if (closedSet[cx][cy] == false &&
                         currentNeighbor.isBlocked == false)
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
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if(this.board[i][j] == this.Start){  
                    System.out.print(" S ");
                }
                else if (this.board[i][j] == this.End) {
                    System.out.print(" E ");
                }
                else if(this.board[i][j].isBlocked == false){
                    // Print the found path using red 'V's
                    if (this.board[i][j].isVisited) 
                        System.out.print("\033[31;1m V \033[0m");
                    else 
                        System.out.print("   ");
                }
                // Walls
                else{
                    System.out.print(" X ");
                }
            }
            System.out.println();
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
