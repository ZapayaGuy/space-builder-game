package projectile;

import main.Vector2;
import ship.Ship;

public class SentinalProjectile extends BasicBulletProjectile{

	public SentinalProjectile(Vector2 origin, Vector2 velocity, Ship ship) {
		super(origin, velocity, ship);
		this.setDamage(2);
		this.setRange(9);
		this.setRadius(0.05);
	}

}
