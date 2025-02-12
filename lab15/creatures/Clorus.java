package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;
import java.util.Map;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.List;

public class Clorus extends Creature {

    /** red color. */
    private static final int r = 34;
    /** green color. */
    private static final int g = 0;
    /** blue color. */
    private static final int b = 231;

    /** creates clorus with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if (!empties.isEmpty()) {
            if (!plips.isEmpty()) {
                Direction moveDir = HugLifeUtils.randomEntry(plips);
                return new Action(Action.ActionType.ATTACK, moveDir);
            } else if (energy >= 1.0) {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.REPLICATE, moveDir);
            } else {
                Direction moveDir = HugLifeUtils.randomEntry(empties);
                return new Action(Action.ActionType.MOVE, moveDir);
            }
        }
        return new Action(Action.ActionType.STAY);
    }

    @Override
    public Color color() {
        return color(r, g, b);
    }
}
