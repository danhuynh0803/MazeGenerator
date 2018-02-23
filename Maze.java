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
                this.board[i][j] = new Node();
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

    //checks to see if current node is in range of grid
    public boolean isValid(int row, int col)
    {
        return (row >=0) && (row < length) && (col >= 0) && (col < width);
    }

    public boolean isBlocked(int row, int col)
    {
        if(board[row][col].isBlocked)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isDestination(int row, int col)
    {
        if(board[row][col] == End)
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

    public void tracePath()
    {
        
    }

    public void aStarSearch()
    {
        Set<Node> openSet = new LinkedHashSet();
        Set<Node> closedSet = new LinkedHashSet();

        // Add the Starting node to the openset
        Node q;
        openSet.add(Start);

        while(!openSet.isEmpty())
        {
            q = getLowestNode(openSet);
        }


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
                    // For displaying connections of each node
                    System.out.print(" " + displayConnections(this.board[i][j]) + " ");

                    //System.out.print("-");
                }
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
