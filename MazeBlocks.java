import java.util.*;

 public class MazeBlocks {
    public int length;
    public int width;
    public Node Start;
    public Node End;
    public Node[][] board;

    public MazeBlocks(int length, int width){
        this.length = length;
        this.width = width;
        this.board= new Node[length][width];
        int [][] board2 = {{1,1,1,1,1,1,1,1,1},
        				   {1,0,0,0,0,0,0,0,1},
        		  		   {1,0,1,0,0,0,0,0,1},
        		  		   {1,0,1,1,1,1,1,0,1},
        		  		   {1,0,0,0,0,0,0,0,1},
        		  		   {1,0,0,0,0,0,0,0,1},
        				   {1,0,0,0,0,0,0,0,1},
        				   {1,1,1,1,1,1,1,1,1}};
        				
        			
        double p = 0.3;
        for(int i=0;i< length;i++){
            for(int j=0;j < width;j++){
                this.board[i][j] = new Node(i, j);
                // Set the node as being a wall
                // depending on the probability 'p' that is passed in
                //if (Math.random() < p)
                if (board2[i][j] == 1)
                {
                    this.board[i][j].isBlocked = true;
                }
            }
        }
		
       
        // Set Start of maze and the End of maze
        // By default these two nodes should be unblocked
        this.Start = this.board[4][5];
        this.Start.f = 0;
        this.Start.g = 0;
        this.Start.h = 0;

        this.End = this.board[1][3];
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
                        System.out.print(" P ");
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
    
}