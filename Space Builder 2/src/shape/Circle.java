package shape;

import main.Vector2;

public class Circle extends Shape {
    private double radius;

    public Circle(Vector2 center, double radius) {
        super(center, null);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean collidesWith(Shape shape) {
        if (shape instanceof Circle) {
            return collidesWithCircle((Circle) shape);
        } else {
            return shape.collidesWithCircle(this);
        }
    }

    protected boolean collidesWithCircle(Circle circle) {
        double distance = getCenter().distance(circle.getCenter());
        return distance <= (this.radius + circle.getRadius());
    }

    public boolean containsPoint(Vector2 point) {
        double distance = getCenter().distance(point);
        return distance <= radius;
    }

    public boolean intersectsSegment(Vector2 start, Vector2 end) {
        // Implement circle-segment intersection test
        Vector2 d = end.subtract(start);
        Vector2 f = start.subtract(getCenter());

        double a = d.dot(d);
        double b = 2 * f.dot(d);
        double c = f.dot(f) - radius * radius;

        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return false;
        }

        discriminant = Math.sqrt(discriminant);

        double t1 = (-b - discriminant) / (2 * a);
        double t2 = (-b + discriminant) / (2 * a);

        if (t1 >= 0 && t1 <= 1 || t2 >= 0 && t2 <= 1) {
            return true;
        }

        return false;
    }
}
