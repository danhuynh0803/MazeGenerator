
public class Maze {
    private int length;
    private int width;
    public Node Start;
    public Node End;
    public Node[][] board;



    public Maze(int length,int width,double p){
        this.length = length;
        this.width = width;
        this.board = new Node[length][width];

        for(int i=0;i< length;i++){
            for(int j=0;j < width;j++){
                this.board[i][j] = new Node(i,j,p);
            }
        }
        for(int i=0;i< length;i++){
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
        }

        this.Start = this.board[0][0];
        this.End = this.board[length-1][width-1];
        this.Start.isBlocked = false;
        this.End.isBlocked = false;

    }


    public void print(){
        for (int i = 0; i < this.length; i++) {
            for (int j = 0; j < this.width; j++) {
                if(this.board[i][j] == this.Start || this.board[i][j] == this.End){
                    System.out.print(" X ");
                }
                else if(this.board[i][j].isBlocked == false){
                    System.out.print(" _ ");
                }
                else{
                    System.out.print(" | ");
                }
            }
            System.out.println();
        }
    }
}
