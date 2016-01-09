package com.bitoaster.game.livingBeing.state;

import com.bitoaster.game.PathFinding;
import com.bitoaster.game.XMLParser;
import com.bitoaster.game.livingBeing.LivingBeing;
import com.bitoaster.game.world.World;
import com.bitoaster.game.livingBeing.LivingBeingsAutomaton;

public class Alive implements LivingBeingsState {

    @Override
    public void move(LivingBeingsAutomaton automate, int[] cord) {
        automate.getLivingBeing().getMovement().move(automate.getLivingBeing(), cord);
    }

    @Override
    public void dead(LivingBeingsAutomaton automate) {
        automate.setState(new Dead());
    }

    @Override
    public void hit(LivingBeingsAutomaton automate, int val) {
        automate.getLivingBeing().suppLife(val);
        if (automate.getLivingBeing().getLife() <= 0){
            automate.getLivingBeing().dead();
        } else if (automate.getLivingBeing().getLife() <= automate.getLivingBeing().getLifeLimit()){
            automate.setState(new Injured());
        }
    }

    @Override
    public void heal(LivingBeingsAutomaton automate, int val) {
        automate.getLivingBeing().addLife(val);
    }

    @Override
    public void resucite(LivingBeingsAutomaton automate){

    }

    @Override
    public void eat(LivingBeingsAutomaton automate, LivingBeing livingBeing) {
        livingBeing.dead();
        automate.getLivingBeing().addEnergy(livingBeing.getValEnergy());
    }

    @Override
    public void reproduction(LivingBeingsAutomaton automate) {
        LivingBeing livingBeing = automate.getLivingBeing();

        int sx = livingBeing.getCord()[0];
        int sy = livingBeing.getCord()[1];

        int tx,ty;
        tx = ty = -1;

        if ( PathFinding.free(new int[] {sx+1, sy+0}) ) {
            tx = sx+1; ty = sy+0;
        } else if ( PathFinding.free(new int[] {sx-1, sy+0}) ) {
            tx = sx-1; ty = sy+0;
        } else if ( PathFinding.free(new int[] {sx+0, sy+1}) ) {
            tx = sx+0; ty = sy+1;
        } else if ( PathFinding.free(new int[] {sx+0, sy-1}) ) {
            tx = sx+0; ty = sy-1;
        }

        if (tx != -1) {
            livingBeing.suppEnergy(50);
            World.getInstance().addLivingBeing(XMLParser.getInstance().createLivingBeings(livingBeing.getId(), tx, ty));
        }
    }

    @Override
    public void instinct(LivingBeingsAutomaton automate) {
        automate.getLivingBeing().getInstinct().instinct();
    }
}
