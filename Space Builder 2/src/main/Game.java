package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;

import block.Block;
import block.Core;
import gun_blocks.*;
import managers.CollisionManager;
import managers.ProjectileManager;
import managers.ShipManager;
import projectile.Projectile;
import ship.FiringMode;
import ship.Ship;
import ship.ShipBuilder;
import ship.Team;

public class Game {
    private Map<Integer, Boolean> keysHeld = new HashMap<>();
    private boolean mouseIsHeld = false;
    private Vector2 mousePosition = new Vector2();
    private ShipManager shipManager = new ShipManager();
    private final Ship playerShip = new Ship(0, 0, Team.PLAYER);
    
    private final ProjectileManager projectileManager = new ProjectileManager();
    private final CollisionManager collisionManger = new CollisionManager(shipManager, projectileManager);
    
    public Game() {
        for (int i = 0; i < Short.MAX_VALUE; i++) {
            keysHeld.put(i, false);
        }

        shipManager.add(playerShip);
        playerShip.setFiringMode(FiringMode.MOUSE_CONTROL);
        
        ShipBuilder shipBuilder = new ShipBuilder();
        
        Core c = new Core(0, 0, 3, 20);
        c.setBlockSize(2);
        shipBuilder.addBlock(c);
                
        shipBuilder.addBlock(new Core(0, 1, 3, 20));
        shipBuilder.addBlock(new StingerGun(0, 2));
        shipBuilder.addBlock(new Sentinal(1, 1));
        

        shipBuilder.build(0, 0, playerShip);
        
        
        Ship s = new Ship(10, 0, Team.DERELICT);
        ShipBuilder s1 = new ShipBuilder();
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                s1.addBlock(new Core(i, j, 3, 3));
            }
        }
        
        s1.build(0, 0, s);
        shipManager.add(s);
    }

    public void update(double time_S) {
    	
    	handlePlayerInput();
    	
    	shipManager.updateAll(time_S);
    	projectileManager.updateAll(time_S);
    	
    	collisionManger.handleCollisions(shipManager);
    }

    private void handlePlayerInput() {
    	Vector2 dir = new Vector2(0, 0);
    	
    	if(keysHeld.get(KeyEvent.VK_W)) dir.y++;
    	if(keysHeld.get(KeyEvent.VK_A)) dir.x--;
    	if(keysHeld.get(KeyEvent.VK_S)) dir.y--;
    	if(keysHeld.get(KeyEvent.VK_D)) dir.x++;
    	
    	if(!dir.equalsZero()) playerShip.moveInDirection(dir.angle());
    	
    	if(keysHeld.get(KeyEvent.VK_E)) {
    		playerShip.rotateInDirection(-1);
    	}
    	if(keysHeld.get(KeyEvent.VK_Q)) {
    		playerShip.rotateInDirection(1);
    	}
    	
    	if(mouseIsHeld) {
    		playerShip.setFiringMode(FiringMode.MOUSE_CONTROL);
    		playerShip.shoot(mousePosition, projectileManager);
    	}
    }
    
    public Vector2 getPlayerCenter() {
        return playerShip.getWorldCenter();
    }

    public Block[] getAllBlocks() {
        return shipManager.getAllBlocks();
    }
    public Projectile[] getAllProjectiles() {
        return projectileManager.getAllProjectiles();
    }

    public KeyListener getKeyListener() {
        return keyListener;
    }

    public MouseListener getMouseListener() {
        return mouseListener;
    }

    private KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {}

        @Override
        public void keyPressed(KeyEvent e) {
            keysHeld.put(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            keysHeld.put(e.getKeyCode(), false);
        }
    };

    public void setMousePosition(double x, double y) {
        mousePosition.x = x;
        mousePosition.y = y;
    }

    private MouseListener mouseListener = new MouseListener() {

        @Override
        public void mousePressed(MouseEvent e) {
            mouseIsHeld = true;
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            mouseIsHeld = false;
        }

        @Override public void mouseClicked(MouseEvent e) {} 
        @Override public void mouseEntered(MouseEvent e) {}
        @Override public void mouseExited(MouseEvent e) {}
    };
}
