package com.bitoaster.game;

import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.world.World;
import org.newdawn.slick.*;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.util.Log;

import java.awt.*;

public class Game extends BasicGame {
    public final static int maxEntities         = 300;  // Max Entities on the map

    public final static int spriteSize          = 32;   // Width / Height of sprite in px
    public final static int spriteSheetWidth    = 3;    // Width of sprite sheet
    public final static int spriteSheetHeight   = 4;    // Height of sprite sheet

    public final static int animationSpeed      = 200;  // duration of step in animation
    public final static int gameSpeed           = 500;  // time between 2 day

    private static int WIDTH  = 800;    // Width of window
    private static int HEIGHT = 608;    // Height of window

    private int time = 0;   // time left in game

    // Singleton
    private static Game ourInstance = new Game();
    public static Game getInstance() {
        return ourInstance;
    }

    private GameContainer container;

    private Game() {
        super("ModularGame");
    }

    public static int getScreenWidth() {
        return WIDTH;
    }
    public static int getScreenHeight() {
        return HEIGHT;
    }

    @Override
    public void init(GameContainer container) throws SlickException {
        this.container = container;

        World.getInstance().getMap().generate();
        World.getInstance().init();

        new Music("./data/sound/high_alert.ogg").loop();
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException {
        World.getInstance().getMap().render(g);
        for (LivingBeing livingBeing : World.getInstance().getLivingBeings()) {
            g.drawAnimation(livingBeing.getSprite(), livingBeing.getCord()[0]*spriteSize, livingBeing.getCord()[1]*spriteSize);
        }
        g.drawString("Entities: "+World.getInstance().getNumberEntities(), 10, 25);
        g.drawString("Day: "+World.getInstance().getDay(), 10, 40);
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException {
        time += delta;
        while (gameSpeed <= time) {
            Log.info("");
            World.getInstance().play();
            time = time - gameSpeed;
        }
        for (LivingBeing livingBeing : World.getInstance().getLivingBeings()) {
            livingBeing.getSprite().update(delta);
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_ESCAPE:
                container.exit();
                break;
        }
    }

    public static void main(String[] args) {
        boolean fullScreen = false;

        for ( int i=0; i<args.length; i++ ){
            switch (args[i].substring(2)) {
                case "debug":
                    Log.setForcedVerboseOn();
                    break;
                case "fullScreen":
                    fullScreen = true;
                    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
                    WIDTH  = (int) screen.getWidth();
                    HEIGHT = (int) screen.getHeight();
                    break;
                case "size":
                    if ( !fullScreen ) {
                        try {
                            WIDTH  = Integer.parseInt(args[i + 1]);
                            HEIGHT = Integer.parseInt(args[i + 2]);

                            i += 2;
                        } catch (Exception e) {
                            Log.warn("incorrect size argument try '--size <width> <height>'");
                        }
                    }
                    break;
                default:
                    Log.warn("unknown argument "+args[i]);
            }
        }

        try {
            AppGameContainer app = new AppGameContainer(Game.getInstance());
            app.setDisplayMode(WIDTH, HEIGHT, fullScreen);
            app.start();
        } catch (SlickException e) {
            Log.error(e);
        }
    }
}
