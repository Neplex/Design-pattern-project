package com.bitoaster.game.livingBeing.state;

import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.livingBeing.LivingBeingsAutomaton;

public class Dead implements LivingBeingsState {

    public void move(LivingBeingsAutomaton automate, int[] cord) {

    }

    public void dead(LivingBeingsAutomaton automate) {

    }

    @Override
    public void hit(LivingBeingsAutomaton automate, int val) {

    }

    @Override
    public void heal(LivingBeingsAutomaton automate, int val) {

    }

    @Override
    public void resucite(LivingBeingsAutomaton automate){
        automate.getLivingBeing().addLife(automate.getLivingBeing().getLifeMax());
    }

    @Override
    public void eat(LivingBeingsAutomaton automate, LivingBeing livingBeing) {

    }

    @Override
    public void reproduction(LivingBeingsAutomaton automate){

    }

    @Override
    public void instinct(LivingBeingsAutomaton automate) {

    }
}
