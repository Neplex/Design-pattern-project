package com.bitoaster.game.livingBeing.state;

import com.bitoaster.game.livingBeing.LivingBeingsAutomaton;

public class Injured extends Alive {

    @Override
    public void heal(LivingBeingsAutomaton automate, int val) {
        automate.getLivingBeing().addLife(val);
        if (automate.getLivingBeing().getLife() > automate.getLivingBeing().getLifeLimit()) {
            automate.setState(new Alive());
        }
    }
}
