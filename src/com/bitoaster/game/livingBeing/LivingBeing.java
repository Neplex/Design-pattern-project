package com.bitoaster.game.livingBeing;

import com.bitoaster.game.Game;
import com.bitoaster.game.PathFinding;
import com.bitoaster.game.livingBeing.strategy.movement.IMovement;
import com.bitoaster.game.livingBeing.strategy.reproduction.IReproduction;
import com.bitoaster.game.world.World;
import org.newdawn.slick.Animation;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.util.Log;
import org.newdawn.slick.util.pathfinding.Mover;

import java.util.Random;

public class LivingBeing implements Mover {

    // Automate (pattern state)
    private LivingBeingsAutomaton automaton;    // Alive, Dead
    private InstinctAutomaton instinct;         // Instinct

    private int life, lifeMax, lifeLimit;       // Life
    private int energy, energyMax, energyLimit; // Energy

    private int valEnergy;                      // Energetic value
    private int[] cord;                         // Position
    private boolean sex;                        // Sex

    private int id;                             // Id
    private String name;                        // Name
    private Integer[] food;                     // Diet

    // Interface (pattern strategy)
    public IMovement movement;                  // Movement
    public IReproduction reproduction;          // Reproduction

    // Graphic elements
    private Animation sprite, up, down, left, right;
    
    public LivingBeing(int x, int y, int id, String name, int valEnergy, int life, int lifeLimit, int energy, int energyLimit, IMovement movement, IReproduction reproduction, Integer[] food){

        automaton = new LivingBeingsAutomaton(this);
        instinct = new InstinctAutomaton(this);

        cord = new int[2];
        cord[0] = x;
        cord[1] = y;

        this.id             = id;
        this.name           = name;
        this.valEnergy      = valEnergy;
        this.food           = food;
        this.sex            = new Random().nextBoolean();

        this.life = lifeMax = life;
        this.lifeLimit      = lifeLimit;

        this.energyMax      = energy;
        this.energy         = energyMax/2;
        this.energyLimit    = energyLimit;

        this.movement       = movement;
        this.reproduction   = reproduction;

        initGraphic();
    }

    // Graphic
    protected void initGraphic() {
        try {
            SpriteSheet spriteSheet = new SpriteSheet(new Image("./data/img/"+name+"/sprite.png"), Game.spriteSize, Game.spriteSize);

            Image[] movementDown, movementLeft, movementRight, movementUp;

            int[] duration = new int[Game.spriteSheetWidth];
            for (int i=0; i<duration.length; i++) {
                duration[i] = Game.animationSpeed;
            }

            movementDown     = new Image[duration.length];
            movementLeft     = new Image[duration.length];
            movementRight    = new Image[duration.length];
            movementUp       = new Image[duration.length];

            for (int i=0; i<duration.length; i++) {
                movementDown[i]  = spriteSheet.getSprite(i, 0);
                movementLeft[i]  = spriteSheet.getSprite(i, 1);
                movementRight[i] = spriteSheet.getSprite(i, 2);
                movementUp[i]    = spriteSheet.getSprite(i, 3);
            }

            up      = new Animation(movementUp,     duration, false);
            down    = new Animation(movementDown,   duration, false);
            left    = new Animation(movementLeft,   duration, false);
            right   = new Animation(movementRight,  duration, false);

            sprite = down;
        } catch (SlickException e) {
            Log.error("Graphic initialisation failed ", e);
        }
    }

    public Animation getSprite() {
        return sprite;
    }

    // Action
    public void move(int[] cord){
        automaton.move(cord);

        if (cord[0] == 1) {
            sprite = right;
        } else if (cord[0] == -1) {
            sprite = left;
        } else if (cord[1] == 1) {
            sprite = down;
        } else if (cord[1] == -1) {
            sprite = up;
        }
    }
    public void dead() {
        setLife(0);
        automaton.dead();
        World.getInstance().delLivingBeings(this);
    }
    public void hit(int val){
        automaton.hit(val);
    }
    public void heal(int val) {
        automaton.heal(val);
    }
    public void resucite() {
        automaton.resucite();
    }
    public void eat(LivingBeing livingBeing) {
        automaton.manger(livingBeing);
    }
    public void instinct() {
        automaton.instinct();
    }
    public void reproduction() {
        automaton.reproduction();
    }

    // Info
    public int getId() {
        return id;
    }
    public Integer[] getFood() {
        return food;
    }
    public boolean getSex() {
        return sex;
    }
    public int getValEnergy() {
        return valEnergy;
    }
    public InstinctAutomaton getInstinct() {
        return instinct;
    }
    public boolean isDead() {
        return automaton.isDead();
    }
    public String getName() {
        return name;
    }

    // Strategy
    public IMovement getMovement() {
        return movement;
    }
    public IReproduction getReproduction() {
        return reproduction;
    }

    // Cord
    public int[] getCord() {
        return cord;
    }
    public void setCord(int[] cord) {
        this.cord = cord;
    }
    public void addCord(int[] cord) {
        if ( PathFinding.free(new int[]{this.cord[0]+cord[0], this.cord[1]+cord[1]}) ) {
            this.cord[0] += cord[0];
            this.cord[1] += cord[1];
        }
    }

    // Life
    public int getLife(){
        return life;
    }
    public void addLife(int val) {
        life += val;
        if (life > lifeMax) {
            life = lifeMax;
        }
    }
    public void suppLife(int val) {
        if (life > 0) {
            life -= val;
        } else {
            this.dead();
        }
    }
    private void setLife(int val) {
        life = val;
    }

    public int getLifeMax() {
        return lifeMax;
    }
    public int getLifeLimit() {
        return lifeLimit;
    }

    // Energy
    public int getEnergy(){
        return energy;
    }
    public void addEnergy(int val) {
        energy += val;
        if (energy > energyMax) {
            energy = energyMax;
        }
    }
    public void suppEnergy(int val) {
        if (energy > 0) {
            energy -= val;
        } else {
            suppLife(val);
        }
    }
    private void setEnergy(int val) {
        energy = val;
    }

    public int getEnergyMax() {
        return energyMax;
    }
    public int getEnergyLimit() {
        return energyLimit;
    }

    // Override method
    @Override
    public String toString() {
        return "<LivingBeing - ["+cord[0]+", "+cord[1]+"]" +
                " life:" + life +
                " energy:" + energy +
                " sex:" + sex +
                ">";
    }
}
