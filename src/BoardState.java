import java.awt.Point;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Represents a single Sokoban BoardState
 */
public class BoardState implements Comparable<BoardState> {
    // Board position in characters
    public static char WALL = '#';
    public static char PLAYER = 'P';
    public static char BOX = 'B';
    public static char STORAGE = 'S';
    public static char EMPTY = ' ';
    public static char BOX_ON_STORAGE = '*';
    public static char PLAYER_ON_STORAGE = '+';
    private char[][] board;
    private Point player;
    private Set<Point> storages;
    private Set<Point> boxes;
    private Point directionTaken;
    private int cost;

    public BoardState(char[][] board, Point player, Set<Point> storages, Set<Point> boxes, Point direction) {
        this.board = board;
        this.player = player;
        this.storages = storages;
        this.boxes = boxes;
        this.directionTaken = direction;
        cost = 0;
    }

    public BoardState(char[][] board, Point player, Set<Point> storages, Set<Point> boxes) {
        this(board, player, storages, boxes, null);
    }
    public boolean isSolved() {
        for (Point storage: storages) {
            if (!(pointExists(storage, STORAGE) && pointExists(storage, BOX))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns whether or not the player can move in a certain direction
     * @param direction the row/col direction
     * @return True if player can move, false otherwise
     */
    public boolean canMove(Point direction) {
        Point newPos = new Point(player.x + direction.x, player.y + direction.y);
        Point OneOutnew = new Point(player.x + direction.x, player.y + direction.y);
        if (pointExists(newPos, BOX) || pointExists(newPos, BOX_ON_STORAGE)) {
            if (pointExists(OneOutnew, WALL) || pointExists(OneOutnew, BOX) || pointExists(OneOutnew, BOX_ON_STORAGE) ) {
                return false;
            }
            return true;
        }
        else if (pointExists(newPos, WALL)) {
            return false;
        }
        return true;
    }
    /**
     * Returns the new BoardState after moving a certain direction
     * @param direction the direction to move
     * @return the new BoardState
     * @pre must be called only if canMove is true
     */
    public BoardState MakeMove(Point direction) {
        Point newPos = new Point(player.x + direction.x, player.y + direction.y);
        Point OneOutnew = new Point(player.x + direction.x, player.y + direction.y);
        Set<Point> newBoxes = boxes;
        char[][] newBoard = new char[board.length][];
        for (int i = 0; i < newBoard.length; i++) {
            newBoard[i] = board[i].clone();
        }
        if (pointExists(newPos, BOX)) {
            newBoard[player.x][player.y] = EMPTY;
            newBoard[newPos.x][newPos.y] = PLAYER;
            newBoard[OneOutnew.x][OneOutnew.y] = BOX;
            newBoxes.remove(newPos);
            newBoxes.add(OneOutnew);
        }
        else {
            newBoard[player.x][player.y] = EMPTY;
            newBoard[newPos.x][newPos.y] = PLAYER;
        }
        return new BoardState(newBoard, newPos, storages, newBoxes, direction);
    }

    /**
     * Gets the current state's cost
     * @return the current state's cost
     */
    public int getCost() {
        return cost;
    }

    /**
     * Gets the current state's storaes
     * @return the current state's storages
     */
    public Set<Point> getStorages() {
        return storages;
    }

    /**
     * Gets the current state's boxes
     * @return the current state's boxes
     */
    public Set<Point> getBoxes() {
        return boxes;
    }

    public boolean pointExists(int row, int col, char object) {
        return (board[row][col] == object);
    }

    public boolean pointExists(Point p, char object) {
        return pointExists(p.x, p.y, object);
    }

    /**
     * Sets the current state's cost
     * @param cost the current state's cost
     */
    public void setCost(int cost) {
        this.cost = cost;
    }

    /**
     * Gets the direction that the player made to get to that BoardState
     * @return the direction that the player made to get to that BoardState
     */
    public Point getDirectionTaken() {
        return directionTaken;
    }

    @Override
    public int compareTo(BoardState other) {
        if (this.getCost() < other.getCost()) {
            return -1;
        }
        else if (this.getCost() > other.getCost()) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                builder.append(board[row][col]);
            }
            builder.append('\n');
        }
        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BoardState other = (BoardState) obj;
        if (!Arrays.deepEquals(board, other.board)) {
            return false;
        }
        if (storages == null) {
            if (other.storages != null) {
                return false;
            }
        }
        else if (!storages.equals(other.storages)) {
            return false;
        }
        if (player == null) {
            if (other.player != null) {
                return false;
            }
        }
        else if (!player.equals(other.player)) {
            return false;
        }
        return true;
    }

    /**
     * Parses a Sokoban text file into a Board object
     * @param boardfile the Sokoban text file
     * @return the Board state object
     * @throws IOException if the Sokoban file does not exist
     */
    public static BoardState parseBoard(String boardfile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(boardfile));
        int width = Integer.parseInt(reader.readLine());
        int height = Integer.parseInt(reader.readLine());
        char[][] newboard = new char[height][width];
        Point player = new Point();
        Set<Point> storages = new HashSet<Point>();
        Set<Point> boxes = new HashSet<Point>();
        String line;
        for (int row = 0; row < height && (line = reader.readLine()) != null; row++) {
            for (int col = 0; col < width && col < line.length(); col++) {
                char item = line.charAt(col);
                newboard[row][col] = item;
                if (item == PLAYER || item == PLAYER_ON_STORAGE) {
                    player = new Point(row, col);
                }
                if (item == STORAGE || item == PLAYER_ON_STORAGE || item == BOX_ON_STORAGE) {
                    storages.add(new Point(row, col));
                }
                if (item == BOX || item == BOX_ON_STORAGE) {
                    boxes.add(new Point(row, col));
                }
            }
        }
        reader.close();
        return new BoardState(newboard, player, storages, boxes);
    }
}
