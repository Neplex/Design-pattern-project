package com.bitoaster.game.livingBeing.strategy.movement;

import com.bitoaster.game.livingBeing.LivingBeing;

public class Pedestrian implements IMovement {
    @Override
    public void move(LivingBeing livingBeing, int[] cord) {
        livingBeing.addCord(cord);
    }
}
