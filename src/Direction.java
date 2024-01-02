import java.awt.*;

/**
 * Wrapper class for Sokoban board movements
 */
public class Direction {
    public static Point UP = new Point (-1, 0);
    public static Point DOWN = new Point (1, 0);
    public static Point RIGHT = new Point (0, 1);
    public static Point LEFT = new Point (0, -1);

    private Direction() {}

    /**
     * Point direction to character mapping for search output
     * @param direction the direction to translate
     * @return the corresponding character mapping
     */
    public static char directionToChar(Point direction) {
        if (direction.equals(UP)) {
            return 'u';
        }
        else if (direction.equals(DOWN)) {
            return 'd';
        }
        else if (direction.equals(RIGHT)) {
            return 'r';
        }
        else if (direction.equals(LEFT)) {
            return 'l';
        }
        throw new IllegalStateException("Non-existent direction: " + direction);
    }

}
