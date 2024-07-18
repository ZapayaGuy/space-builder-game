package managers;

import java.util.ArrayList;
import java.util.List;

import block.Block;
import ship.Ship;

public class ShipManager {
	private ArrayList<Ship> ships = new ArrayList<>();
	
	public ShipManager() {};
	
	public void add(Ship ship) {
		ships.add(ship);
	}
	
	public void updateAll(double time_S) {
        for (Ship ship : ships) {
            ship.update(time_S);
        }
	}
	
	public Block[] getAllBlocks() {
        List<Block> blocks = new ArrayList<>();

        for (Ship ship : ships) {
            blocks.addAll(ship.getBlocks());
        }

        return blocks.toArray(new Block[0]);
	}
	
	public ArrayList<Ship> getShips() {
		return ships;
	}
}
