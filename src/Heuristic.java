import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class Heuristic {

    /**
     * Calculates the cost of each state
     * Uses the Manhattan Heuristic
     * @param state
     */
    public void score(BoardState state) {
        Set<Point> storages = state.getStorages();
        Set<Point> boxes = state.getBoxes();

        Set<Point> intersection = new HashSet<Point>(storages);
        intersection.retainAll(boxes);
        storages.removeAll(intersection);
        boxes.removeAll(intersection);

        int cost = 0;
        for (Point box: boxes) {
            int mindist = Integer.MAX_VALUE;
            for (Point storage : storages) {
                int dist = getDistance(box, storage);
                if (dist < mindist) {
                    mindist = dist;
                }
            }
            cost += mindist;
        }
        state.setCost(cost);
    }

    public static int getDistance(Point p1, Point p2) {
        return Math.abs(p1.x - p2.x) + Math.abs(p1.y - p2.y);
    }
}
