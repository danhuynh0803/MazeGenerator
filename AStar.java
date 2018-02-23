import java.lang.Math;
import java.util.*;

public class AStar
{
    final int ROW;
    final int COL;
    double f, g, h;
    public AStar(int length, int width, double f, double g, double h)
    {
        this.ROW = 101;
        this.COL = 101;
        this.f = f;
        this.g = g;
        this.h = h;
    }

    //checks to see if current node is in range of grid
    public boolean isValid(int row, int col)
    {
        return (row >=0) && (row < ROW) && (col >= 0) && (col < COL);
    }

    public boolean isBlocked(Node [][] grid, int row, int col)
    {
        if(grid[row][col].isBlocked)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isDestination(int row, int col, Node [][] grid)
    {
        if(grid[row][col] == grid[ROW-1][COL-1])
            return true;
        else   
            return false;
    }

    public double calculateHValue(int row, int col)
    {
        return ((double) Math.sqrt((row-(ROW-1)*(row-(ROW-1)) + (col-(COL-1)*(col-(COL-1))))));
    }

    public void tracePath()
    {
        
    }

    public void aStarSearch()
    {

    }
}