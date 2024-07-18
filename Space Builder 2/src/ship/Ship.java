package ship;

import java.util.ArrayList;

import block.Block;
import block.Core;
import block.Thruster;
import gun_blocks.Turret;
import main.Vector2;
import managers.ProjectileManager;
import projectile.Projectile;

public class Ship {
	protected ArrayList<Block> blocks = new ArrayList<>();
	
	private FiringMode firingMode = FiringMode.SHOOT_AT_CLOSEST;
	
	private Team team;
		
	private double rotationAngle = 0, angularVelocity = 0;
	private Vector2 shipPositionOrigin = new Vector2(0, 0), worldCenter, velocity = new Vector2(), appliedForce = new Vector2();
	private double appliedTorque = 0;
	
	public Ship(double x, double y, Team team) {
		super();
		this.team = team;

		this.worldCenter = new Vector2(x, y);
	}

	public int getBlockLimit() {
		return blocks.stream().filter(block -> block instanceof Core).mapToInt(block -> ((Core)block).getBlockLimit()).sum();
	}

	public double getTotalTorque() {
		double sum = 0;
		
		for(Block block : blocks) {
			if(block instanceof Core) {
				Core core = (Core) block;
				sum += core.getTorque();
			} 
		}
		
		return sum;
	}
	
	public void applyTorque(double torque) {
		appliedTorque += torque;
	}	
	public ArrayList<Block> getBlocks() {
		return blocks;
	}
	
	public void addBlock(Block b) {
		b.setParentShip(this);
		blocks.add(b);
		updateWorldCenter();
	}
	
	protected void addBlockWithoutUpdatingCenterOfMass(Block b) {
		b.setParentShip(this);
		blocks.add(b);
	}
	
	public void addBlocks(Block[] b) {
		for(int i = 0; i < b.length; i++) {
			b[i].setParentShip(this);
			blocks.add(b[i]);
		}
		updateWorldCenter();
	}
	
	public void removeBlock(Block b) {
		b.setParentShip(null);
		blocks.remove(b);
		updateWorldCenter();
	}
    public void applyForce(double fx, double fy, double worldPointX, double worldPointY) {
        appliedForce.x += fx;
        appliedForce.y += fy;
        
        Vector2 force = new Vector2(fx, fy);
        Vector2 point = new Vector2(worldPointX, worldPointY);
        Vector2 leverArm = point.subtract(worldCenter);
        
        double torque = leverArm.cross(force);
        
        applyTorque(torque);
    }
    public void applyForce(Vector2 force, Vector2 point) {
        appliedForce = appliedForce.add(force);

    	point = point.clone();    	
        
        Vector2 leverArm = point.subtract(worldCenter);
        
        double torque = leverArm.cross(force);
        
        applyTorque(torque);
    }
	public void update(double time_S) {
		for(Block b : blocks) {
			if(b instanceof BlockUpdateable) {
				((BlockUpdateable)b).update(time_S);
			}
			
			if(b.getAppliedForce().equalsZero()) {
				applyForce(b.getAppliedForce(), b.getWorldPosition());
			}
			
			b.resetAppliedForce();
		}
		
		velocity = velocity.add(appliedForce.multiply(time_S/getTotalMass()));
		worldCenter = worldCenter.add(velocity.multiply(time_S));
				
		angularVelocity += time_S * appliedTorque/getMomentOfInertia();
		rotationAngle += angularVelocity * time_S;
		
		//update blocks
		for(int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			
			if(b.isRequestingDeleteFromWorld()) {
				removeBlock(b);
				i--;
			}
			
			
			//set b's new world location
			double angle = this.getRotationAngle() + b.getAngleRelativeToShip();
			b.setRotationAngle(angle);
			
			double xRelative = b.getShipPosition().x;
			double yRelative = b.getShipPosition().y;
			
			b.setWorldLocation(
					this.worldCenter.x + xRelative * Math.cos(this.getRotationAngle()) - yRelative * Math.sin(this.getRotationAngle()), 
					this.worldCenter.y + xRelative * Math.sin(this.getRotationAngle()) + yRelative * Math.cos(this.getRotationAngle()) 
			);
		}
				
		//reset all flags
		appliedTorque = 0; 
		appliedForce.setValues(0, 0);
	};
	
	public void merge(Ship ship2, Vector2 ship2AnchorPosition) {
		Block[] blocks = ship2.getBlocks().toArray(new Block[ship2.getBlocks().size()]);
		
		for(int i = 0; i < blocks.length; i++) {
			blocks[i].setShipPosition(blocks[i].getShipPosition().add(ship2AnchorPosition));
			
			ship2.removeBlock(blocks[i]);
		}
		
		this.addBlocks(blocks);
	}

    protected void updateWorldCenter() {
        double totalMass = getTotalMass();
        
        Vector2 sum = new Vector2(0, 0);

        for (Block b : blocks) {
        	sum = sum.add(b.getShipPosition().multiply(b.getMass()/totalMass));
        }
        
        double angle = this.getRotationAngle() + sum.angle();
       
        Vector2 newWorldCenter = new Vector2(
                this.getWorldLocation().x + Math.cos(angle) * sum.magnitude(),
                this.getWorldLocation().y + Math.sin(angle) * sum.magnitude()
        );
        
        shipPositionOrigin = shipPositionOrigin.subract(newWorldCenter);
        
        this.setWorldLocation(newWorldCenter);
        
        for (Block b : blocks) {
            b.setShipPosition(b.getShipPosition().subtract(sum));
        }
    }

	
    private void setWorldLocation(Vector2 v) {
    	this.worldCenter = v;
	}

	private Vector2 getWorldLocation() {
    	return this.worldCenter;
	}

	public double getMomentOfInertia() {
        double sum = 1;
        
        for (Block b : blocks) {
            double distanceSquared = b.getShipPosition().x * b.getShipPosition().x + b.getShipPosition().y*b.getShipPosition().y;
            sum += b.getMass() * distanceSquared;
        }
        
        return sum;
    }
			
	public void rotateInDirection(double direction) {
		this.applyTorque(direction * this.getTotalTorque());
	}
	public void moveInDirection(double direction) {
		//implement using linear algrabra prob
		
		//completely temporary only for testing purposes
		double power = 10;
		this.applyForce(new Vector2(
				Math.cos(direction) * power,
				Math.sin(direction) * power
				
				), this.worldCenter);
		
		
	}

	
	public Team getTeam() {
		return team;
	}
	
	public Vector2 getWorldCenter() {
		return worldCenter;
	}

	public double getRotationAngle() {
		rotationAngle %= Math.PI * 2;
		
		while(rotationAngle < 0) {
			rotationAngle += Math.PI * 2;
		}
		
		return this.rotationAngle;
	}

	public Vector2 getShipPositionOrigin() {
		return shipPositionOrigin;
	}

	public void setShipPositionOrigin(Vector2 shipPositionOrigin) {
		this.shipPositionOrigin = shipPositionOrigin;
	}
	public int getTotalBlocks() {
		return blocks.size();

	}
	public double getTotalMass() {
		return blocks.stream().mapToDouble(Block::getMass).sum();
	}

	public void setWorldLocation(double x, double y) {
		this.worldCenter.x = x;
		this.worldCenter.y = y;
	}

	public FiringMode getFiringMode() {
		return firingMode;
	}

	public void setFiringMode(FiringMode firingMode) {
		this.firingMode = firingMode;
	}

	public void shoot(Vector2 mousePosition, ProjectileManager projectileManager) {
		blocks.stream()
		.filter(block -> block instanceof Turret)
		.forEach(block -> ((Turret)block).shoot(mousePosition, projectileManager));
	}

	public Vector2 getVelocity() {
		return this.velocity;
	}


}
