package projectile;

import java.util.ArrayList;
import java.util.List;

import block.Block;
import main.Vector2;
import shape.Circle;
import shape.Shape;
import ship.Ship;

public abstract class BasicBulletProjectile extends Projectile implements ProjectileUpdateable {
	private Vector2 worldPosition, velocity, shipVelocity;
	
	public Vector2 getWorldPosition() {
		return worldPosition;
	}

	public void setWorldPosition(Vector2 worldPosition) {
		this.worldPosition = worldPosition;
	}

	private double radius, damage, range;
	private boolean hitBlock = false;

	public BasicBulletProjectile(Vector2 origin, Vector2 velocity, Ship ship) {
		super(origin, ship);
		this.shipVelocity = ship.getVelocity().clone();
		this.velocity = velocity.clone().add(shipVelocity);
		this.worldPosition = origin.clone();
	}
	
//	public boolean collidesWith(Ship ship) {
//		if(this.getOriginShip() == ship) return false;
//		
//		for(Block block : ship.getBlocks()) {
//			Vector2[] vertexes = new Vector2[4];
//			
//			double halfSize = block.getBlockSize()/2;
//	        vertexes[0] = new Vector2(-halfSize, -halfSize);
//	        vertexes[1] = new Vector2(halfSize, -halfSize);
//	        vertexes[2] = new Vector2(halfSize, halfSize);
//	        vertexes[3] = new Vector2(-halfSize, halfSize);
//	        
//	        for(int i = 0; i < 4; i++) {
//	        	vertexes[i] = CollisionUtil.rotatePoint(vertexes[i], block.getRotationAngle());
//	        	vertexes[i] = vertexes[i].add(block.getWorldPosition());
//	        }
//	        
//			if(CollisionUtil.isCircleIntersectingPolygon(this.worldPosition, radius, vertexes)) {
//				return true;
//			}
//			
//		};
//		
//        return false;
//	}
	
	@Override
	public void update(double time_S) {
		worldPosition = worldPosition.add(velocity.multiply(time_S));
		
		this.origin = origin.add(shipVelocity.multiply(time_S));
	}


	@Override
	public void handleCollision(Block block) {
		if (hitBlock) return;
		
		block.takeDamage(damage);
		hitBlock = true;
	}
	
	private Block getCollidedBlock(Ship ship) {
		Block closestBlock = null;
		
		for(Block block : ship.getBlocks()) {
			Vector2[] vertexes = new Vector2[4];
			
			double halfSize = block.getBlockSize()/2;
	        vertexes[0] = new Vector2(-halfSize, -halfSize);
	        vertexes[1] = new Vector2(halfSize, -halfSize);
	        vertexes[2] = new Vector2(halfSize, halfSize);
	        vertexes[3] = new Vector2(-halfSize, halfSize);
	        
	        for(int i = 0; i < 4; i++) {
	        	vertexes[i] = CollisionUtil.rotatePoint(vertexes[i], block.getRotationAngle());
	        	vertexes[i] = vertexes[i].add(block.getWorldPosition());
	        }
	        
			if(CollisionUtil.isCircleIntersectingPolygon(this.worldPosition, radius, vertexes)) {
				if(closestBlock == null || worldPosition.distanceSqrd(block.getWorldPosition()) < worldPosition.distanceSqrd(closestBlock.getWorldPosition())) {
					closestBlock = block;
				}
			}
			
		};
		
        return closestBlock;
	}

	@Override
	public boolean isRequestingDeleteFromWorld() {
		if(hitBlock || isOutOfRange()) return true;
		
		return false;
	}
	
	private boolean isOutOfRange() {
		if(worldPosition.distanceSqrd(getOrigin()) >= range * range) return true;
		
		return false;
	}
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public double getDamage() {
		return damage;
	}

	public void setDamage(double damage) {
		this.damage = damage;
	}

	public double getRange() {
		return range;
	}

	public void setRange(double range) {
		this.range = range;
	}
	
	public Shape getHitBox() {
		return new Circle(this.getWorldPosition(), radius);
	}


}
