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
        this.Start = this.board[1][1]; // Starting point
        this.Current = this.Start;	
        // TODO play around with where to put the End node
        this.End = this.board[10][10];	// Goal node
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
        for (int x = 0; x < length+2; ++x)
        {
            for (int y = 0; y < width+2; ++y)
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

    public int getLength(){
    	return this.length;
    }
    
    public int getWidth(){
    	return this.width;
    }

    public void print(){
        for (int i=1; i< width+1;i++){
            for(int j=1; j < length+1;j++){
                if(this.board[j][i].northwall){
                    System.out.print("+---");
                }
                else if (this.board[j][i].isVisited) {
                    //System.out.print("+ " + "\033[31;1mV\033[0m" + " ");
                	System.out.print("+ " + " " + " ");
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
                    else if (this.board[j][i].isVisited) {
                        //System.out.print("| V ");
                        //System.out.print("| \033[31;1mV\033[0m ");
                    	System.out.print("+ " + "P" + " ");
                    }
                    else {
                        System.out.print("|   ");
                    }
                }
                else {
                    if(this.board[j][i] == Start){
                        System.out.print("  A ");
                    }
                    else if (this.board[j][i] == End) {
                        System.out.print("  E ");
                    }
                    else if (this.board[j][i].isVisited) {
                        //System.out.print(" \033[31;1mV\033[0m  ");
                    	System.out.print("  " + "P" + " ");
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
                        //System.out.print("+ " + "\033[31;1mV\033[0m" + " ");
                    	System.out.print("+ " + " " + " ");
                    }
                    else {
                        System.out.print("+   ");
                    }
                }
                System.out.println("+");

            }
        }
    }

}
