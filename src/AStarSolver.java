import java.awt.*;
import java.util.*;

public class AStarSolver {
    private BoardState currentState;
    private HashSet<BoardState> visited;
    private HashMap<BoardState, BoardState> backtrack;
    private Queue<BoardState> queue;
    private int seenbefore;
    private Heuristic heuristic;




    public AStarSolver(BoardState BeginningState) {
        currentState = BeginningState;
        visited = new HashSet<BoardState>();
        backtrack = new HashMap<BoardState, BoardState>();
        queue = new PriorityQueue<BoardState>();
        seenbefore = 0;
        heuristic = new Heuristic();
    }

    /**
     * Searches the Sokoban puzzle for solution and returns the move sequence
     * @return the Sokoban solution move sequence
     * @throws NoSolutionException if no solution is found
     */

    public String search() throws NoSolutionException {
        queue.add(currentState);
        while (!queue.isEmpty()) {
            currentState = queue.poll();
            if (visited.contains(currentState)) {
                seenbefore++;
            }
            visited.add(currentState);
            if (currentState.isSolved()) {
                System.out.println(currentState);
                String solution = backtrackmoves(currentState);
                return solution;
            }
            ArrayList<BoardState> validMoves = getValidMoves();
            searchFunction(validMoves);
        }
        throw new NoSolutionException();
    }

    /**
     * Returns the valid moves from the current state.
     * @return the valid moves from the current state.
     */
    public ArrayList<BoardState>  getValidMoves() {
         ArrayList<BoardState> validmoves = new ArrayList<BoardState>(4);
         addIfValid(validmoves, Direction.UP);
         addIfValid(validmoves, Direction.DOWN);
         addIfValid(validmoves, Direction.RIGHT);
         addIfValid(validmoves, Direction.LEFT);
         return validmoves;
    }
    public void addIfValid(ArrayList<BoardState> moves, Point direction) {
        if (currentState.canMove(direction)) {
            BoardState newState = currentState.MakeMove(direction);
            if (!visited.contains(newState)) {
                moves.add(newState);
            }
        }
    }

    /**
     * Search function
     * @param validmoves list of valid moves
     */
    public void searchFunction(ArrayList<BoardState> validmoves) {
        for (BoardState move: validmoves) {
            backtrack.put(move, currentState);
            heuristic.score(move);
            queue.add(move);
        }
    }

    /**
     * Backtracks through the search to find the move sequence
     * @param finalState the final, storage state
     * @return the Sokoban solution move sequence
     */
    public String backtrackmoves(BoardState finalState) {
        // Backtracking solutions and adding moves to stack
        LinkedList<Character> moveStack = new LinkedList<>();
        BoardState current = finalState;
        while (current.getDirectionTaken() != null) {
            char move = Direction.directionToChar(current.getDirectionTaken());
            moveStack.push(move);
            current = backtrack.get(current);
        }

        //Comma delimiting solution
        StringBuilder solution = new StringBuilder();
        String delim = "";
        for (Character move : moveStack) {
            solution.append(delim);
            solution.append(move);
            delim = ", ";
        }
        return solution.toString();
    }






}
