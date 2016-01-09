package com.bitoaster.game.livingBeing.instinct;

import com.bitoaster.game.livingBeing.InstinctAutomaton;
import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.PathFinding;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.Path;

import java.util.Random;

public class Nutritional implements InstinctState {
    @Override
    public void instinct(InstinctAutomaton automate) {

        LivingBeing livingBeing = automate.getLivingBeing();
        LivingBeing food = PathFinding.SearchFood(automate.getLivingBeing());

        if ( food != null ) {

            if (PathFinding.distance(livingBeing, food.getCord()) <= 1) {
                livingBeing.eat(food);
                Log.info(automate.getLivingBeing().getName() + " eat " + food.getName() + " at [" + food.getCord()[0] + ", " + food.getCord()[1] + "]");
            } else {
                Path path = PathFinding.pathFinding(livingBeing, food.getCord());
                if (path != null)
                    livingBeing.move(new int[]{path.getX(1)- livingBeing.getCord()[0], path.getY(1)- livingBeing.getCord()[1]});
            }
        } else {
            livingBeing.move(PathFinding.randomCoord());
        }

        if (livingBeing.getEnergy() > livingBeing.getEnergyLimit()) {
            automate.setState(new Reproduction());
        }
    }
}
