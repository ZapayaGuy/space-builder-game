package gun_blocks;

import block.Block;
import main.Vector2;
import managers.ProjectileManager;

public abstract class Turret extends Block{	
	public Turret(double x, double y) {
		super(x, y);
	}
	
	public abstract void shoot(double x, double y, ProjectileManager projectileManager);

	public void shoot(Vector2 mousePosition, ProjectileManager projectileManager) {
		this.shoot(mousePosition.x, mousePosition.y, projectileManager);
	}
}
