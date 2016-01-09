package com.bitoaster.game;

import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.livingBeing.strategy.movement.*;
import com.bitoaster.game.livingBeing.strategy.reproduction.IReproduction;
import com.bitoaster.game.livingBeing.strategy.reproduction.Reproducible;
import com.bitoaster.game.livingBeing.strategy.reproduction.Unreproducible;
import com.bitoaster.game.world.World;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.newdawn.slick.util.Log;

import java.io.File;
import java.util.List;
import java.util.Random;

public class XMLParser {
    private final static String livingBeings = "./data/livingBeings.xml";

    private static org.jdom2.Document document;
    private static Element racine;

    // Singleton
    private static XMLParser ourInstance = new XMLParser();
    public static XMLParser getInstance() {
        return ourInstance;
    }

    private XMLParser() {
        SAXBuilder sxb = new SAXBuilder();
        try {
            Log.info("Load: " + livingBeings);
            document = sxb.build(new File(livingBeings));
        }
        catch(Exception e){
            Log.error("Load: Failed");
        }

        racine = document.getRootElement();
        Log.info("Load: Finish");
    }

    public int[] getIds() {
        List list = racine.getChildren("livingBeing");
        int[] res = new int[list.size()];

        for (int i=0; i<list.size(); i++) {
            res[i] = Integer.parseInt(((Element) list.get(i)).getChild("id").getText());
        }

        return res;
    }

    public LivingBeing createLivingBeings(Integer id, Integer x, Integer y) {
        Element livingBeings = getLivingBeings(id);

        IMovement movement;
        switch (livingBeings.getChild("movement").getText()) {
            case "Pedestrian":
                movement = new Pedestrian();
                break;
            case  "Fixed":
                movement = new Fixed();
                break;
            default:
                movement = new Pedestrian();
                break;
        }

        IReproduction reproduction;
        switch (livingBeings.getChild("reproduction").getText()) {
            case "Reproducible":
                reproduction = new Reproducible();
                break;
            case  "Unreproducible":
                reproduction = new Unreproducible();
                break;
            default:
                reproduction = new Reproducible();
                break;
        }

        List<Element> foods = livingBeings.getChild("food").getChildren();
        Integer[] food = new Integer[foods.size()];

        for (int i=0; i<foods.size(); i++) {
            food[i] = Integer.parseInt(foods.get(i).getText());
        }

        Log.info("Create "+livingBeings.getChild("name").getText()+" at ["+x+", "+y+"]");

        return new LivingBeing(
                x, y, id,
                livingBeings.getChild("name").getText(),
                Integer.parseInt(livingBeings.getChild("valEnergy").getText()),
                Integer.parseInt(livingBeings.getChild("life").getText()),
                Integer.parseInt(livingBeings.getChild("lifeLimit").getText()),
                Integer.parseInt(livingBeings.getChild("energy").getText()),
                Integer.parseInt(livingBeings.getChild("energyLimit").getText()),
                movement, reproduction, food
                );
    }

    public LivingBeing createLivingBeings(Integer id) {
        int x, y;
        x = y = -1;

        while ( !PathFinding.free(new int[]{x, y}) ){
            x = new Random().nextInt(World.getInstance().getWidth());
            y = new Random().nextInt(World.getInstance().getHeight());
        }

        return createLivingBeings(id, x, y);
    }

    private Element getLivingBeings(int id) {
        List list = racine.getChildren("livingBeing");
        Element livingBeings = null;

        for (int i=0; i<list.size(); i++) {
            if ( Integer.parseInt(((Element) list.get(i)).getChild("id").getText()) == id ) {
                livingBeings = (Element) list.get(i);
                break;
            }
        }
        return livingBeings;
    }
}
