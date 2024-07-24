package main;

import ship.Ship;

public class Vector2 implements Cloneable{
	public double x, y;
	
	public Vector2(double x, double y){
		this.x = x; 
		this.y = y;
	}
	public Vector2(Vector2 vec2){
		this.x = vec2.x;
		this.y = vec2.y;
	}
	public Vector2(){
		this(0, 0);
	}
	
	public void setValues(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2 add(double x, double y) {
		return new Vector2(this.x + x, this.y + y);
	}
	public Vector2 add(Vector2 vec2) {
		return new Vector2(x + vec2.x, y + vec2.y);
	}
	public Vector2 multiply(double factor) {
		return new Vector2(x * factor, y * factor);
	}
	public Vector2 divide(double quotient) {
		return new Vector2(x/quotient, y/quotient);
	}
	
	public double hypotSqrd() {
		return x*x + y*y;
	}
	
	@Override
	public Vector2 clone() {
		return new Vector2(this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "[x=" + x + ",y=" + y + "]";
	}
	public Vector2 subtract(Vector2 vec2) {
		return new Vector2(this.x - vec2.x, this.y - vec2.y);
	}
    public double cross(Vector2 v) {
        return this.x * v.y - this.y * v.x;
    }
	public double magnitude() {
		return Math.sqrt(x*x+y*y);
	}
	public double angle() {
		return Math.atan2(y, x);
	}
	public Vector2 setMagnitude(double magnitude) {
		double r = y/x;
		
		return new Vector2(
				1.0/Math.sqrt(r*r+1),
				r/Math.sqrt(r*r+1)
		).multiply(magnitude);
	};
	public Vector2 subract(double x, double y) {
		return new Vector2(this.x - x, this.y - y);
	}
	public Vector2 subract(Vector2 vec2) {
		return new Vector2(this.x - vec2.x, this.y + vec2.y);
	}
	public double magnitudeSquared() {
		return x*x+y*y;
	}
	
	public static Vector2 newZeroVector() {
		return new Vector2(0, 0);
	}
	
	public double distance(Vector2 vec2) {
		double dx = this.x - vec2.x;
		double dy = this.y - vec2.y;
		
		return Math.sqrt(dx*dx+dy*dy);
	}
	public double distanceSqrd(Vector2 vec2) {
		double dx = this.x - vec2.x;
		double dy = this.y - vec2.y;
		
		return dx*dx+dy*dy;
	}
	
	public boolean equalsZero() {
		return this.x == 0 && this.y == 0;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Vector2)) return false;
		
		Vector2 vec2 = (Vector2) obj;
		
		if(this.x == vec2.x && this.y == vec2.y) return true;
		
		return false;
		
	}
	
	public void setToZero() {
		x = 0; y = 0;
	}
	
	@Override
	public int hashCode() {
		return 31 * Double.hashCode(x) + Double.hashCode(y);
	}
	public double dot(Vector2 vec2) {
		return this.x * vec2.x + this.y * vec2.y;
	}
	public static Vector2 angleMagnitude(double angle, double magnitude) {
		return new Vector2(Math.cos(angle) * magnitude, Math.sin(angle) * magnitude);
	}
	public void setEqual(Vector2 vec2) {
		this.x = vec2.x;
		this.y = vec2.y;
	}
    public Vector2 rotate(double angle) {
        double cosTheta = Math.cos(angle);
        double sinTheta = Math.sin(angle);

        return new Vector2(x * cosTheta - y * sinTheta, x * sinTheta + y * cosTheta);
    }
}
