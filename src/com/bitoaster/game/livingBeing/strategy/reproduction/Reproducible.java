package com.bitoaster.game.livingBeing.strategy.reproduction;

import com.bitoaster.game.PathFinding;
import com.bitoaster.game.livingBeing.LivingBeing;
import org.newdawn.slick.util.pathfinding.Path;

public class Reproducible implements IReproduction {

    @Override
    public void reproduction(LivingBeing livingBeing) {

        LivingBeing partner = PathFinding.searchPartner(livingBeing);

        if ( partner != null ) {

            if (PathFinding.distance(livingBeing, partner.getCord()) <= 1 && livingBeing.getSex()) {
                livingBeing.reproduction();
            } else {
                Path path = PathFinding.pathFinding(livingBeing, partner.getCord());
                if (path != null)
                    livingBeing.move(new int[]{path.getX(1)- livingBeing.getCord()[0], path.getY(1)- livingBeing.getCord()[1]});
            }
        } else {
            livingBeing.move(PathFinding.randomCoord());
        }
    }
}
