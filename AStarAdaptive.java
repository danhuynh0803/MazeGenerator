import java.util.LinkedHashSet;
import java.util.Set;

public class AStarAdaptive {
	private Maze maze;
	private MazeBlocks MazeBlocks;
	private Node Start;
	private Node End;
	private int length;
	private int width;
	private int expanded;
	private boolean reached;
	
	AStarAdaptive(Maze m){
		this.maze = m;
		this.length = m.getLength();
		this.width = m.getWidth();
		this.Start = m.Start;
		this.End = m.End;
        this.expanded = 0;
        this.reached = false;
	}
	
	AStarAdaptive(MazeBlocks m){
		this.MazeBlocks = m;
		this.length = m.length;
		this.width = m.width;
		this.Start = m.Start;
		this.End = m.End;
        this.expanded = 0;
        this.reached = false;
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
        boolean[][] closedSet = new boolean[length+2][width+2];

        // Add the Starting node to the openset
        Start.g = 0;
        Start.h = 0;
        Start.f = 0;
        openSet.add(Start);

        Node lowestFNode = null;
        while(!openSet.isEmpty() && !reached)
        {
        	
            lowestFNode = getLowestNode(openSet);
            //System.out.println("Next node: \t x:" + lowestFNode.x + "\ty:" + lowestFNode.y);

            // pop the lowestFNode from the open set
            // while adding it to the closedSet.
            // This indicates that the node was found to be the best option
            openSet.remove(lowestFNode);
            closedSet[lowestFNode.x][lowestFNode.y] = true;

            // Generate the the neighbors and recalculate the f(n) = g(n) + h(n)
            this.expanded += lowestFNode.neighbors.size() - 1;
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
                    tracePath(currentNeighbor);
                    hasPath = true;
                    reached = true;
                    
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
            
            for(int i=0; i< lowestFNode.neighbors.size();i++){
            	Node currentNeighbor = lowestFNode.neighbors.get(i);
            	currentNeighbor.h = End.g - currentNeighbor.g;
            }
            
        // Trace that path based on the last visited node
        // If a viable path is found, then this should start at End
        //tracePath(lowestFNode);       
        }
        
        // No viable path available   
        if (hasPath&& reached)
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
    
    // Method for debugging purposes
    // Counts and returns the number of 
    // adjecent connections for a particular node
    static int displayN(Node node)
    {
        return node.neighbors.size();
    }

    public int getExpanded(){
    	return this.expanded;
    }
}
