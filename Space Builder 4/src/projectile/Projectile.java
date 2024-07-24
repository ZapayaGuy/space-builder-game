package projectile;

import block.Block;
import main.Vector2;
import shape.Shape;
import ship.Ship;

public abstract class Projectile {
	protected Vector2 origin;
	private final Ship originShip;
	
	public Projectile(Vector2 origin, Ship originShip){
		this.origin = origin.clone();
		this.originShip = originShip;
	}
	
	public Vector2 getOrigin() {
		return origin;
	}
	
	public abstract boolean isRequestingDeleteFromWorld();
	public abstract Shape getHitBox();
	public abstract void handleCollision(Block block);

	public Ship getOriginShip() {
		return originShip;
	}
}
