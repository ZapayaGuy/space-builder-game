package block;

public class Core extends Block{
	private int blockLimit;
	private double torque;
	
	public Core(double x, double y, int blockLimit, double torque) {
		super(x, y);
		this.blockLimit = blockLimit;
		this.torque = torque;
	}
	
	public int getBlockLimit() {
		return blockLimit;
	}
	public double getTorque() {
		return torque;
	}
}
