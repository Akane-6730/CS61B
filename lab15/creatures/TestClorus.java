package creatures;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

public class TestClorus {

    @Test
    public void testBasics() {
        Clorus c = new Clorus(1.0);
        assertEquals(1.0, c.energy(), 0.01);
        assertEquals(new Color(34, 0, 231), c.color());
        c.move();
        assertEquals(0.97, c.energy(), 0.01);
        c.stay();
        assertEquals(0.96, c.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus parent = new Clorus(1.0);
        Clorus child = parent.replicate();
        assertEquals(0.5, parent.energy(), 0.001);
        assertEquals(0.5, child.energy(), 0.001);
        assertNotSame(parent, child);
    }

    @Test
    public void testAttack() {
        Clorus c = new Clorus(1.0);
        Plip p = new Plip(0.5);
        c.attack(p);
        assertEquals(1.5, c.energy(), 0.001);
    }

    @Test
    public void testChooseActionWhenNoEmpty() {
        Clorus c = new Clorus(1.0);
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionStay() {
        // Create a Clorus with energy 0.5
        Clorus c = new Clorus(0.5);

        // Create a map of directions with all impassible objects
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        // The Clorus should STAY because there are no empty spaces
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionAttack() {
        // Create a Clorus with enough energy to attack
        Clorus c = new Clorus(2);

        // Create a map of directions with a Plip present
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Plip(1));  // Plip can be attacked
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        // The Clorus should attack the Plip because it's a valid target
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);
        assertEquals(expected, actual);

        // Create a Clorus with enough energy to attack
        Clorus c2 = new Clorus(2);

        // Create a map of directions with a Plip present
        HashMap<Direction, Occupant> surrounded2 = new HashMap<>();
        surrounded2.put(Direction.TOP, new Plip(1));  // Plip can be attacked
        surrounded2.put(Direction.BOTTOM, new Empty());
        surrounded2.put(Direction.LEFT, new Impassible());
        surrounded2.put(Direction.RIGHT, new Impassible());

        // The Clorus should attack the Plip because it's a valid target
        Action actual2 = c2.chooseAction(surrounded2);
        Action expected2 = new Action(Action.ActionType.ATTACK, Direction.TOP);
        assertEquals(expected2, actual2);
    }

    @Test
    public void testChooseActionReplicate() {
        // Create a Clorus with energy greater than or equal to 1 (enough for replication)
        Clorus c = new Clorus(2);

        // Create a map of directions with only empty spaces
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        // The Clorus should replicate to the first available empty space
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.REPLICATE, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChooseActionMove() {
        // Create a Clorus with not enough energy to replicate or attack
        Clorus c = new Clorus(0.5);

        // Create a map of directions with only empty or impassible spaces
        HashMap<Direction, Occupant> surrounded = new HashMap<>();
        surrounded.put(Direction.TOP, new Empty());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        // The Clorus should move to an empty space since it can't replicate or attack
        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.MOVE, Direction.TOP);
        assertEquals(expected, actual);
    }

    @Test
    public void testChoose() {
        Clorus c = new Clorus(1.8);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = c.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        surrounded.put(Direction.RIGHT, new Empty());
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.REPLICATE, actual.type);
        assertEquals(Direction.RIGHT, actual.dir);

        surrounded.put(Direction.LEFT, new Plip(1));
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.ATTACK, actual.type);
        assertEquals(Direction.LEFT, actual.dir);

        surrounded.put(Direction.LEFT, new Impassible());
        actual = c.chooseAction(surrounded);
        assertEquals(Action.ActionType.REPLICATE, actual.type);
        assertEquals(Direction.RIGHT, actual.dir);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestClorus.class));
    }
}