package managers;

import java.util.ArrayList;

import block.Block;
import projectile.Projectile;
import projectile.ProjectileUpdateable;
import ship.Ship;

public class ProjectileManager {
	private ArrayList<Projectile> projectiles = new ArrayList<>();
	
	public ProjectileManager() {};
	
	public void addProjectile(Projectile projectile){
		projectiles.add(projectile);
	}
	public void removeProjectile(Projectile projectile){
		projectiles.remove(projectile);
	}

	public void updateAll(double time_S) {
		ArrayList<Projectile> projectilesToRemove = new ArrayList<>(0);
		
		for(Projectile projectile : projectiles) {
			
			if(projectile instanceof ProjectileUpdateable)((ProjectileUpdateable)projectile).update(time_S);
			
			if(projectile.isRequestingDeleteFromWorld()) {
				projectilesToRemove.add(projectile);
			}
		
		
		}
		
		projectiles.removeAll(projectilesToRemove);
	}

	public Projectile[] getAllProjectiles() {
		return projectiles.toArray(new Projectile[projectiles.size()]);
	}
	
	
}
