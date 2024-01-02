import java.io.IOException;

public class SokobanSolver {
    public static void parseArguments(String puzzlepath) {
        try {
            BoardState initialBoard = BoardState.parseBoard(puzzlepath);
            AStarSolver solver = new AStarSolver(initialBoard);
            System.out.println(initialBoard);
            if (solver != null) {
                String solution = solver.search();
                System.out.println("Solution: " + solution);
            }
        }
        catch (NoSolutionException e) {
            System.out.println("Solution does not exist");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
