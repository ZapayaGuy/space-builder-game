package managers;

import block.Block;
import projectile.Projectile;
import shape.Shape;

public class CollisionManager {
	private ShipManager shipManager;
	private ProjectileManager projectileManager;
	
	public CollisionManager(ShipManager shipManager, ProjectileManager projectileManager) {
		this.setShipManager(shipManager);
		setProjectileManager(projectileManager);
	}
	
	public void handleCollisions(ShipManager shipManager) {
		//optimize this HEAVILY
		for(Projectile projectile : projectileManager.getAllProjectiles()) {
			for(Block block : shipManager.getAllBlocks()) {
				if(projectile.getOriginShip() == block.getParentShip()) continue;
				
				Shape blockHitBox = block.getHitBox();
				Shape projectileHitBox = projectile.getHitBox();
				
				if(blockHitBox.collidesWith(projectileHitBox)) {
					projectile.handleCollision(block);
				}
			}
		}
	}

	public ShipManager getShipManager() {
		return shipManager;
	}

	public void setShipManager(ShipManager shipManager) {
		this.shipManager = shipManager;
	}
	public ProjectileManager getProjectileManager() {
		return projectileManager;
	}

	public void setProjectileManager(ProjectileManager projectileManager) {
		this.projectileManager = projectileManager;
	}


}
