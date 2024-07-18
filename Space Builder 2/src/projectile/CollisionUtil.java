package projectile;

import main.Vector2;

public class CollisionUtil {

    public static boolean isCircleIntersectingPolygon(Vector2 circleCenter, double circleRadius, Vector2[] polygon) {
        // Check for each edge of the polygon
        for (int i = 0; i < polygon.length; i++) {
            Vector2 p1 = polygon[i];
            Vector2 p2 = polygon[(i + 1) % polygon.length];
            Vector2 edge = p2.subtract(p1);
            Vector2 axis = new Vector2(-edge.y, edge.x); // Perpendicular to edge

            // Project the circle and the polygon onto the axis
            double[] circleProjection = projectCircle(circleCenter, circleRadius, axis);
            double[] polygonProjection = projectPolygon(polygon, axis);

            // Check for overlap
            if (!overlap(circleProjection, polygonProjection)) {
                return false; // Separating axis found, no collision
            }
        }
        return true; // No separating axis found, collision detected
    }

    public static Vector2 rotatePoint(Vector2 point, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = point.x * cos - point.y * sin;
        double y = point.x * sin + point.y * cos;
        return new Vector2(x, y);
    }

    private static double[] projectCircle(Vector2 center, double radius, Vector2 axis) {
        double projectionCenter = center.dot(axis);
        return new double[]{projectionCenter - radius, projectionCenter + radius};
    }

    private static double[] projectPolygon(Vector2[] polygon, Vector2 axis) {
        double min = Double.MAX_VALUE;
        double max = Double.MIN_VALUE;
        for (Vector2 point : polygon) {
            double projection = point.dot(axis);
            if (projection < min) min = projection;
            if (projection > max) max = projection;
        }
        return new double[]{min, max};
    }

    private static boolean overlap(double[] range1, double[] range2) {
        return range1[1] >= range2[0] && range2[1] >= range1[0];
    }
}
