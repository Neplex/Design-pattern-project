package com.bitoaster.game.world;

import com.bitoaster.game.Game;
import com.bitoaster.game.PathFinding;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.PathFindingContext;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import java.util.Random;

public class Map implements TileBasedMap {
    private int WIDTH, HEIGHT;          // Taille
    private final int wall=10;          // chance of wall

    private Image grass, block;         // Images
    private boolean[][] blocked;        // Walls

    public Map(int w, int h) {
        WIDTH = w;
        HEIGHT = h;
    }

    public void generate(){
        blocked = new boolean[WIDTH][HEIGHT];

        try {
            SpriteSheet spriteSheet = new SpriteSheet("./data/img/tile-set.png", Game.spriteSize, Game.spriteSize);

            grass = spriteSheet.getSprite(2, 0);
            block = spriteSheet.getSprite(5, 0);
        } catch (SlickException e) {
            Log.error(e);
        }

        for (int x = 0; x< WIDTH; x++) {
            for (int y = 0; y< HEIGHT; y++) {
                blocked[x][y] = new Random().nextInt(wall) == 0;
            }
        }
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public boolean blocked(int[] cord) {
        try {
            return blocked[cord[0]][cord[1]];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.warn("blocked method out of map");
            return true;
        }
    }

    public void render(Graphics g){
        for (int x=0; x<WIDTH; x++) {
            for (int y=0; y<HEIGHT; y++) {
                g.drawImage(grass, x* Game.spriteSize, y*Game.spriteSize);
                if (blocked[x][y]) {
                    g.drawImage(block, x*Game.spriteSize, y*Game.spriteSize);
                }
            }
        }
    }

    @Override
    public int getWidthInTiles() {
        return WIDTH;
    }

    @Override
    public int getHeightInTiles() {
        return HEIGHT;
    }

    @Override
    public void pathFinderVisited(int i, int i1) {
    }

    @Override
    public boolean blocked(PathFindingContext pathFindingContext, int i, int i1) {
        return !PathFinding.free(new int[]{i, i1});
    }

    @Override
    public float getCost(PathFindingContext pathFindingContext, int i, int i1) {
        return 1;
    }
}
