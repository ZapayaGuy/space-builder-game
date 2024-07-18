package block;

import main.Vector2;
import shape.Shape;
import ship.Ship;

public abstract class Block {
    private static final int DEFAULT_BLOCK_SIZE = 1;
    private static final double DEFAULT_BLOCK_HEALTH = 1;
    private static final double DEFAULT_BLOCK_MASS = 1;

    
    private Ship parentShip;
    private int blockSize = DEFAULT_BLOCK_SIZE;
    private double health = DEFAULT_BLOCK_HEALTH;
    private double mass = DEFAULT_BLOCK_MASS;
    private double rotationAngle, angleRelativeToShip;
    private Vector2 worldPosition = new Vector2(0, 0), shipPosition, appliedForce = new Vector2(0, 0);

    protected Block(double x, double y) {
        this(new Vector2(x, y));
    }

    protected Block(Vector2 position) {
        this.worldPosition = position;
        this.shipPosition = position;
    }

    public void takeDamage(double damage) {
        health -= damage;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getMass() {
        return mass;
    }

    public double getBlockSize() {
        return blockSize;
    }

    public boolean isRequestingDeleteFromWorld() {
        return isDead();
    }

    public Vector2 getShipPosition() {
        return shipPosition;
    }

    public void setShipPosition(double x, double y) {
        this.shipPosition = new Vector2(x, y);
    }

    public void setShipPosition(Vector2 position) {
        this.shipPosition = position;
    }

    public double getAngleRelativeToShip() {
        angleRelativeToShip = angleRelativeToShip % (Math.PI * 2);
        return angleRelativeToShip;
    }

    public void setAngleRelativeToShip(double angleRelativeToShip) {
        this.angleRelativeToShip = angleRelativeToShip % (Math.PI * 2);
    }

    public double getRotationAngle() {
		rotationAngle %= Math.PI * 2;
		
		while(rotationAngle < 0) {
			rotationAngle += Math.PI * 2;
		}

        return rotationAngle;
    }
    
    public void setBlockSize(int blockSize) {
    	this.blockSize = blockSize;
    }
    
    public void setRotationAngle(double rotationAngle) {
		rotationAngle %= Math.PI * 2;
		
		while(rotationAngle < 0) {
			rotationAngle += Math.PI * 2;
		}

        this.rotationAngle = rotationAngle;
    }

    public Vector2 getWorldPosition() {
        return worldPosition;
    }

    public void setWorldPosition(Vector2 vec2) {
        worldPosition = vec2;
    }

    public void setWorldLocation(double x, double y) {
        worldPosition = new Vector2(x, y);
    }

    public void setMass(double mass) {
        this.mass = mass;
    }

    public Vector2 getAppliedForce() {
        return appliedForce;
    }

    public void setAppliedForce(Vector2 appliedForce) {
        this.appliedForce = appliedForce;
    }
    
    public void resetAppliedForce() {
    	this.appliedForce.setToZero();
    }

	public Ship getParentShip() {
		return parentShip;
	}

	public void setParentShip(Ship parentShip) {
		this.parentShip = parentShip;
	}

    public Shape getHitBox() {
        Vector2[] vertices = new Vector2[4];
        double halfSize = blockSize / 2.0;
        
        // Calculate the unrotated vertices relative to the center
        vertices[0] = new Vector2(-halfSize, -halfSize);
        vertices[1] = new Vector2(halfSize, -halfSize);
        vertices[2] = new Vector2(halfSize, halfSize);
        vertices[3] = new Vector2(-halfSize, halfSize);

        // Rotate the vertices around the center and translate to world position
        for (int i = 0; i < vertices.length; i++) {
            vertices[i] = vertices[i].rotate(rotationAngle).add(worldPosition);
        }

        return new Shape(worldPosition, vertices);
    }
}
