package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable {
    final int originalTitleSize = 16;
    final int scale = 3;

    // screen settings
    final public int tileSize = originalTitleSize * scale;
    final public int maxScreenCol = 16;
    final public int maxScreenRow = 12;
    final public int screenWidth = tileSize * maxScreenCol;
    final public int screenHeight = tileSize * maxScreenRow;

    // world map settings
    final public int maxWorldCol = 50;
    final public int maxWorldRow = 50;
    final public int worldWidth  = tileSize * maxWorldCol;
    final public int worldHeight = tileSize * maxWorldRow;


    final double FPS = 60;

    KeyHandler keyH = new KeyHandler();
    Thread gameThread;
    public Player player = new Player(this, keyH);
    TileManager tileM = new TileManager(this);
    public CollisionChecker cChecker = new CollisionChecker(this);
    public SuperObject[] obj = new SuperObject[10];
    public AssetSetter aSetter = new AssetSetter(this);

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void setUpGame() {
        aSetter.setObject();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start(); //will call run()
    }

    @Override
    public void run() { //automatically calls this method to run in its seperate thread
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                // update information eg.character position
                update();
                // draw the screen with the updated information
                repaint();

                delta--;
            }
        }
    }

    public void update() {
        player.update();
    }

    public void paintComponent(Graphics g) {
       super.paintComponent(g); // pre-defined method to paint the screen

        Graphics2D g2 = (Graphics2D)g;
        tileM.draw(g2);
        for (SuperObject o : obj) {
            if (o != null) {
                o.draw(g2, this);
            }
        }
        player.draw(g2); //this will draw the player over the tiles draw sort of like overwritting
        g2.dispose(); // good practice to save memory
    }
}
