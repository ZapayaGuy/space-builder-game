package gun_blocks;

import main.Vector2;
import managers.ProjectileManager;
import projectile.SentinalProjectile;
import projectile.StingerProjectile;
import ship.BlockUpdateable;

public class Sentinal extends Turret implements BlockUpdateable{
	private static final double BULLET_SPEED = 50;
	private static final double DEVIATION = Math.PI/20;

	private double fireInterval = 0.36;
	
	private double timer = 0;
	
	public Sentinal(double x, double y) {
		super(x, y);
	}

	@Override
	public void shoot(double x, double y, ProjectileManager projectileManager) {
		if(!canShoot()) return;
		
		double angle = Math.atan2(y - this.getWorldPosition().y, x - this.getWorldPosition().x);
		
		timer = 0;
				
		projectileManager.addProjectile(
				new SentinalProjectile(this.getWorldPosition(),
						Vector2.angleMagnitude(angle + DEVIATION * Math.random() - DEVIATION/2, BULLET_SPEED), this.getParentShip()));
	}

	
	private boolean canShoot() {
		if(timer >= fireInterval) return true;
		
		return false;
	}
	
	@Override
	public void update(double time_S) {
		timer += time_S;
		
		if(timer > fireInterval) timer = fireInterval;
	}

}
