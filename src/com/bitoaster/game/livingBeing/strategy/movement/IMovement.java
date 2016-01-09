package com.bitoaster.game.livingBeing.strategy.movement;

import com.bitoaster.game.livingBeing.LivingBeing;

public interface IMovement {
    void move(LivingBeing livingBeing, int[] cord);
}
