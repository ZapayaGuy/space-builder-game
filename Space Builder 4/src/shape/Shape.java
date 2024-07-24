package shape;

import main.Vector2;

public class Shape {
    private final Vector2 center;
    private final Vector2[] vertices;

    public Shape(Vector2 center, Vector2[] vertices) {
        this.center = center;
        this.vertices = vertices;
    }

    public Vector2[] getVertices() {
        return vertices;
    }

    public Vector2 getCenter() {
        return center;
    }

    public boolean collidesWith(Shape shape) {
        if (shape instanceof Circle) {
            return collidesWithCircle((Circle) shape);
        } else {
            return collidesWithPolygon(shape);
        }
    }

    protected boolean collidesWithCircle(Circle circle) {
        // Implement collision detection between this shape (assumed to be a polygon) and a circle
        for (Vector2 vertex : vertices) {
            if (circle.containsPoint(vertex)) {
                return true;
            }
        }

        // Check if any edge of the polygon intersects the circle
        for (int i = 0; i < vertices.length; i++) {
            Vector2 start = vertices[i];
            Vector2 end = vertices[(i + 1) % vertices.length];
            if (circle.intersectsSegment(start, end)) {
                return true;
            }
        }

        return false;
    }

    private boolean collidesWithPolygon(Shape polygon) {
        // Implement collision detection between two polygons
        Vector2[] otherVertices = polygon.getVertices();
        for (Vector2 vertex : vertices) {
            if (polygon.containsPoint(vertex)) {
                return true;
            }
        }
        for (Vector2 vertex : otherVertices) {
            if (containsPoint(vertex)) {
                return true;
            }
        }

        return false;
    }

    public boolean containsPoint(Vector2 point) {
        // Implement point-in-polygon test
        boolean result = false;
        for (int i = 0, j = vertices.length - 1; i < vertices.length; j = i++) {
            if ((vertices[i].y > point.y) != (vertices[j].y > point.y) &&
                (point.x < (vertices[j].x - vertices[i].x) * (point.y - vertices[i].y) / (vertices[j].y - vertices[i].y) + vertices[i].x)) {
                result = !result;
            }
        }
        return result;
    }
}
