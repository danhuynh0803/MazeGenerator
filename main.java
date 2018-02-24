
public class main {
    public static void main(String[] args) {
        Maze b = new Maze(50,50,0.3);
        b.print();
        b.aStarSearch();
        System.out.println("Printing path");
        b.print();
    }
}
