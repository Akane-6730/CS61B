package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {
    private int period;
    private int state;
    private final double factor;

    public AcceleratingSawToothGenerator(int period, double factor) {
        this.factor = factor;
        this.period = period;
        state = 0;
    }

    // Normalize the state to map it between -1.0 and 1.0
    private double normalize(int state) {
        // State is between 0 and period - 1, we map this to the range -1.0 to 1.0
        return 2.0 * state / period - 1.0;
    }

    @Override
    public double next() {
        state = (state + 1) % period;
        if (state == 0) {
            period = (int) (period * factor);
        }
        return normalize(state);
    }
}
