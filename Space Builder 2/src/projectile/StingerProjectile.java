package projectile;

import main.Vector2;
import ship.Ship;

public class StingerProjectile extends BasicBulletProjectile{

	public StingerProjectile(Vector2 origin, Vector2 velocity, Ship ship) {
		super(origin, velocity, ship);
		this.setDamage(5);
		this.setRange(20);
		this.setRadius(0.2);
	}

}
