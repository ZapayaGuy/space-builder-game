package gun_blocks;

import main.Vector2;
import managers.ProjectileManager;
import projectile.StingerProjectile;
import ship.BlockUpdateable;

public class StingerGun extends Turret implements BlockUpdateable{
	private static final double BULLET_SPEED = 50;
	private static final double DEVIATION = Math.PI/30;
	
	private double fireInterval = 0.6;
	
	private double timer = 0;
	
	public StingerGun(double x, double y) {
		super(x, y);
	}

	@Override
	public void shoot(double x, double y, ProjectileManager projectileManager) {
		if(!canShoot()) return;
		
		double angle = Math.atan2(y - this.getWorldPosition().y, x - this.getWorldPosition().x);
						
		projectileManager.addProjectile(
				new StingerProjectile(this.getWorldPosition(),
						Vector2.angleMagnitude(angle + Math.random() * DEVIATION - DEVIATION/2, BULLET_SPEED), this.getParentShip()));
	
		timer = 0;
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
