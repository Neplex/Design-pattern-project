package com.bitoaster.game;

import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.world.World;
import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.Random;

public final class PathFinding {

    public static LivingBeing SearchFood(LivingBeing livingBeing) {
        int distance = -1;
        LivingBeing res = null;

        for (LivingBeing e: World.getInstance().getLivingBeings()) {

            for (Integer id: livingBeing.getFood()) {

                if ( id == e.getId() ) {

                    int dist = distance(livingBeing, e.getCord());

                    if (dist < distance || distance == -1) {
                        distance = dist;
                        res = e;
                    }
                }
            }
        }

        return res;
    }

    public static LivingBeing searchPartner(LivingBeing livingBeing) {
        int distance = -1;
        LivingBeing res = null;

        for (LivingBeing e: World.getInstance().getLivingBeings()) {

            if (e.getId() == livingBeing.getId() && e.getSex() != livingBeing.getSex()) {

                int dist = distance(livingBeing, e.getCord());

                if (dist < distance || distance == -1) {
                    distance = dist;
                    res = e;
                }
            }
        }

        return res;
    }

    public static Path pathFinding(LivingBeing start, int[] cord) {
        //Log.debug("Start pathFinding for "+start.getCord()[0]+"/"+start.getCord()[1]+" to "+cord[0]+"/"+cord[1]);
        AStarPathFinder pathFinder = new AStarPathFinder(World.getInstance().getMap(), 40, false);
        return pathFinder.findPath(
                start,
                start.getCord()[0], start.getCord()[1],
                cord[0], cord[1]
        );
    }

    public static int distance(LivingBeing start, int[] cord) {
        AStarPathFinder pathFinder = new AStarPathFinder(World.getInstance().getMap(), 40, false);
        return (int) pathFinder.getHeuristicCost(
                start,
                start.getCord()[0], start.getCord()[1],
                cord[0], cord[1]
        );
    }

    public static boolean free(int[] cord) {
        if (World.getInstance().getMap().blocked(cord)) {
            return false;
        } else {
            for (LivingBeing livingBeing : World.getInstance().getLivingBeings()) {
                if (livingBeing.getCord() == cord) {
                    return false;
                }
            }
        }
        return true;
    }

    public static int[] randomCoord() {
        int[][] coord = new int[][]{ {0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        return coord[new Random().nextInt(coord.length)];
    }
}
