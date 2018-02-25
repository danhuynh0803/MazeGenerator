
public class main {
    public static void main(String[] args) {
    	long  startTime, endTime, elapsedTime;
    	
    	
        //Maze b = new Maze(20,20);
        //b.print();
    	
    	MazeBlocks m = new MazeBlocks(8,9);
    	m.print();
    	//AStar astar1 = new AStar(m);
    	//m.print();

        
        //b.aStarSearch();
        
        //Node n = new Node(1,1);
        
        AStar astar = new AStar(m);
        startTime = System.currentTimeMillis();
        astar.aStarSearch();
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Time elapsed on Repeated Forward A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + astar.getExpanded());
        m.print();
        
        AStarAdaptive aAdaptive = new AStarAdaptive(m);
        startTime = System.currentTimeMillis();
        aAdaptive.aStarSearch();
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        System.out.println("Time elapsed on Adaptive A*: " + elapsedTime + " ms");
        System.out.println("Number of expanded node: " + aAdaptive.getExpanded());
        m.print();
        
        /*
        AStarBackward astarbackward = new AStarBackward(b);
        
        startTime = System.currentTimeMillis();
        astarbackward.aStarSearch();
        endTime = System.currentTimeMillis();
        elapsedTime = endTime - startTime;
        
        //System.out.println("Number of expanded node:" + astarbackward.getNumofexpandedNode());
        System.out.println("Time elapsed on Repeated Backward A*: " + elapsedTime + " ms");
        b.print();
        */
        
        //AStarAdaptive astaradaptive = new AStarAdaptive(b);
        //astaradaptive.aStarSearch();
        //System.out.println("Number of expanded node: " + astaradaptive.getExpanded());
        //b.print();
    }
}
