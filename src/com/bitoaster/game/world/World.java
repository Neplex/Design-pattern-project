package com.bitoaster.game.world;

import com.bitoaster.game.Game;
import com.bitoaster.game.XMLParser;
import com.bitoaster.game.livingBeing.LivingBeing;
import org.newdawn.slick.util.Log;

import java.util.ArrayList;

public class World {
    private long day;                            // Day number
    private Map map;                             // Map

    private ArrayList<LivingBeing> tmp;          // Liste suivante
    private ArrayList<LivingBeing> livingBeings; // Liste des etres vivants

    // Singleton
    private static World ourInstance = new World(
            Game.getScreenWidth ()/Game.spriteSize,
            Game.getScreenHeight()/Game.spriteSize
    );
    public static World getInstance() {
        return ourInstance;
    }

    private World(int w, int h) {
        tmp = new ArrayList<>();
        livingBeings = new ArrayList<>();

        map = new Map(w, h);
    }

    public void init() {
        Log.info("Init: Start");
        for (int k = 0; k < 15; k++) {
            livingBeings.add(XMLParser.getInstance().createLivingBeings(21));
        }
        for (int k = 0; k < 10; k++) {
            livingBeings.add(XMLParser.getInstance().createLivingBeings(111));
        }
        for (int k = 0; k < 5; k++) {
            livingBeings.add(XMLParser.getInstance().createLivingBeings(22));
        }
        Log.info("Init: Complete");
    }

    public void play() {
        day++;
        tmp = (ArrayList) livingBeings.clone();
        Log.info("Day "+day);
        for (LivingBeing livingBeing : livingBeings) {
            livingBeing.suppEnergy(1);
            livingBeing.instinct();
        }
        if ( day%2==0 ) {
            addLivingBeing(XMLParser.getInstance().createLivingBeings(111));
        }
        livingBeings = (ArrayList) tmp.clone();
        Log.info(getNumberEntities()+" living beings");
    }

    public ArrayList<LivingBeing> getLivingBeings() {
        return livingBeings;
    }

    public Map getMap() {
        return map;
    }
    public int getWidth() {
        return map.getWIDTH();
    }
    public int getHeight() {
        return map.getHEIGHT();
    }

    public void addLivingBeing(LivingBeing livingBeing) {
        if ( tmp.size() < Game.getInstance().maxEntities ) {
            tmp.add(livingBeing);
        }
    }
    public void delLivingBeings(LivingBeing livingBeing) {
        tmp.remove(livingBeing);
    }

    public int getNumberEntities() {
        return livingBeings.size();
    }
    public long getDay() {
        return day;
    }
}
