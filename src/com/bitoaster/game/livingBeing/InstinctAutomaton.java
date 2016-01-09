package com.bitoaster.game.livingBeing;

import com.bitoaster.game.livingBeing.instinct.InstinctState;
import com.bitoaster.game.livingBeing.instinct.Reproduction;

public class InstinctAutomaton {
    private LivingBeing livingBeing;
    private InstinctState state;

    public InstinctAutomaton(LivingBeing livingBeing) {
        state = new Reproduction();
        this.livingBeing = livingBeing;
    }

    public void setState(InstinctState state) { this.state = state; }
    public LivingBeing getLivingBeing() {
        return livingBeing;
    }

    public void instinct() {
        state.instinct(this);
    }
}
