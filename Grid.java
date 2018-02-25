import java.util.*;

public class Grid 
{
    public int x; //x-axis on grid
    public int y; //y-axis on grid
    public Node open; //node to be evaluated
    public Node close; //node already evaluated
    public Node[][] grid;

    public Grid(int x, int y)
    {
        this.x = x;
        this.y = y;
        this.grid = new Node[x][y];

        //creates the grid before starting
        for(int i = 0; i < this.x; i++)
        {
            for(int j = 0; j < this.y; j++)
            {
                this.grid[i][j] = this.grid[i+1][j+1];
            }
        }
    }

    
}