package com.bitoaster.game.livingBeing.instinct;

import com.bitoaster.game.livingBeing.InstinctAutomaton;
import com.bitoaster.game.livingBeing.LivingBeing;

public class Reproduction implements InstinctState {

    @Override
    public void instinct(InstinctAutomaton automate) {

        LivingBeing livingBeing = automate.getLivingBeing();

        livingBeing.getReproduction().reproduction(livingBeing);

        if (livingBeing.getEnergy() <= livingBeing.getEnergyLimit()) {
            automate.setState(new Nutritional());
        }
    }
}
