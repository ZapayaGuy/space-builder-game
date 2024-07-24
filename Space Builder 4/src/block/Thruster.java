package block;

import main.Vector2;

public class Thruster extends Block{

	private double maxThrust, power;
	
	public Thruster(double x, double y, double maxThrust) {
		super(x, y);
		this.maxThrust = maxThrust;
	}
	
	
	public void setPower(double power) {
		this.power = power;
	}
	public double getPower() {
		return power;
	}
	
	public void activate() {
		double angle = this.getRotationAngle();

		setAppliedForce(Vector2.angleMagnitude(angle, getCurrentThrust()));
	}
	public void activate(double power) {
		double angle = this.getRotationAngle();
		
		setAppliedForce(Vector2.angleMagnitude(angle, getThrust(power)));
	}
	
	public double getCurrentThrust() {
		return maxThrust * power;
	}
	public double getThrustAngle() {
		return this.getRotationAngle();
	}
	public double getThrust(double power) {
		return maxThrust * power;
	}
}
