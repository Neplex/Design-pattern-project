package com.bitoaster.game.livingBeing;

import com.bitoaster.game.livingBeing.state.*;

public class LivingBeingsAutomaton {
    private LivingBeing livingBeing;
    private LivingBeingsState state;

    public LivingBeingsAutomaton(LivingBeing livingBeing) {
        state = new Alive();
        this.livingBeing = livingBeing;
    }

    public void setState(LivingBeingsState state) { this.state = state; }
    public LivingBeing getLivingBeing() {
        return livingBeing;
    }

    // Action
    protected void move(int[] cord){
        state.move(this, cord);
    }
    protected void dead(){
        state.dead(this);
    }
    protected void hit(int val) {
        state.hit(this, val);
    }
    protected void heal(int val) {
        state.heal(this, val);
    }
    protected void resucite() {
        state.resucite(this);
    }
    protected void manger(LivingBeing livingBeing) {
        state.eat(this, livingBeing);
    }
    protected void reproduction() {
        state.reproduction(this);
    }
    protected void instinct() {
        state.instinct(this);
    }

    // Info
    protected boolean isDead() {
        return state.equals(new Dead());
    }
}
