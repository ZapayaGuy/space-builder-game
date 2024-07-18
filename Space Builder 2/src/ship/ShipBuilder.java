package ship;

import java.util.ArrayList;

import block.Block;

public class ShipBuilder {
	private ArrayList<Block> blocks;
	
	public ShipBuilder() {
		blocks = new ArrayList<>();
	}
	public ShipBuilder(ArrayList<Block> blocks) {
		this.blocks = blocks;
	}
	
	public void addBlock(Block b) {
		blocks.add(b);
	}
	
	public void build(double x, double y, Ship ship) {
		for(int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			b.setShipPosition(b.getShipPosition().add(x, y));
			ship.addBlockWithoutUpdatingCenterOfMass(b);
		}
		
		ship.updateWorldCenter();
	}
		
}
