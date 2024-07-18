package main;

import java.awt.Color;
import java.awt.GridBagLayout;

import javax.swing.JFrame;

public class Main {
    public static int targetFrameRate = 120;
    public static double frameInterval_S = 1.0 / targetFrameRate;
    public static long frameIntervalNs = 1_000_000_000 / targetFrameRate;

    public static void main(String args[]) {
        JFrame frame = new JFrame("My Space Game");
        Game game = new Game();
        GamePanel gamePanel = new GamePanel(game);

        frame.setLayout(new GridBagLayout());
        frame.add(gamePanel);

        frame.setSize(1024, 768 + 28);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setBackground(Color.LIGHT_GRAY);

        gamePanel.setFocusable(true);
        gamePanel.addKeyListener(game.getKeyListener());
        gamePanel.addMouseListener(game.getMouseListener());

        // Game loop
        boolean running = true;
        long previousTime = System.nanoTime();
        long delta = 0;

        while (running) {
            long currentTime = System.nanoTime();
            delta += currentTime - previousTime;
            previousTime = currentTime;

            while (delta >= frameIntervalNs) {
                game.setMousePosition(gamePanel.getMouseGamePosition().x, gamePanel.getMouseGamePosition().y);
                game.update(frameIntervalNs / 1_000_000_000.0);
                delta -= frameIntervalNs;
            }

            gamePanel.repaint();

            long sleepTime = frameIntervalNs - delta;
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1_000_000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
