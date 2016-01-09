package com.bitoaster.game.livingBeing.state;

import com.bitoaster.game.livingBeing.LivingBeingsAutomaton;
import com.bitoaster.game.livingBeing.LivingBeing;

public interface LivingBeingsState {
    void move(LivingBeingsAutomaton automate, int[] cord);
    void dead(LivingBeingsAutomaton automate);
    void hit(LivingBeingsAutomaton automate, int val);
    void heal(LivingBeingsAutomaton automate, int val);
    void resucite(LivingBeingsAutomaton automate);
    void eat(LivingBeingsAutomaton automate, LivingBeing livingBeing);
    void reproduction(LivingBeingsAutomaton automate);
    void instinct(LivingBeingsAutomaton automate);
}
