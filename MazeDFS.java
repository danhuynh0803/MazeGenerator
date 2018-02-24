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
		/*for(int i=0;i< length;i++){
			for(int j=0;j< width;j++){
				if( i>0){
					this.board[i][j].west = this.board[i-1][j];
				}
				if( i< this.length-1){
					this.board[i][j].east = this.board[i+1][j];
				}
				if( j< this.width-1){
					this.board[i][j].south = this.board[1][j+1];
				}
				if (j > 0){
					this.board[i][j].north = this.board[i][j-1];
				}
			}
		}*/
		
		init();
        generate();
        this.Start = this.board[1][1];
        this.Current = this.Start;
        //this.End = this.board[length-1][width-1];
        //this.End.isBlocked = false;
		
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
	
	public void print(){
		for (int i=1; i< width+1;i++){
			for(int j=1; j < length+1;j++){
				if(this.board[j][i].northwall){
					System.out.print("+---");
				}
				else {
					System.out.print("+   ");
				}
			}
			System.out.println("+");
			
			for(int j=1;j < length+1;j++){
				if(this.board[j][i].westwall){
					if(this.Current == this.board[j][i]){
						System.out.print("| A ");
					}
					else
						System.out.print("|   ");
				}
				else {
					if(this.Current == this.board[j][i]){
						System.out.print("| A ");
					}
					else
					System.out.print("    ");
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
				else {
					System.out.print("+   ");
				}
			}
			System.out.println("+");
			
			}
		}
	}
}
