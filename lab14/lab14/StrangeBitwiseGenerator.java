package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {

    private int period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    // Normalize the state to map it between -1.0 and 1.0
    private double normalize(int state) {
        // State is between 0 and period - 1, we map this to the range -1.0 to 1.0
        return 2.0 * state / period - 1.0;
    }

    @Override
    public double next() {
        // Increment the state and reset it once it reaches the period
        state = (state + 1);
        int weirdState = state & (state >>> 3) % period;
        weirdState = state & (state >> 3) & (state >> 8) % period;
        // Normalize and return the next value
        return normalize(weirdState);
    }
}
