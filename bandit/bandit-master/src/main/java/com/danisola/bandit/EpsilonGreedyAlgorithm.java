package com.danisola.bandit;

import java.util.ArrayList;
import java.util.Random;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * {@link com.danisola.bandit.BanditAlgorithm} that chooses a random lever with epsilon-frequency,
 * and otherwise chooses the lever with the highest estimated mean, based on the rewards observed thus far.
 *
 * http://en.wikipedia.org/wiki/Multi-armed_bandit#Semi-uniform_strategies
 */
public class EpsilonGreedyAlgorithm extends AbstractBanditAlgorithm {

    private final double epsilon;
    private final Random random = new Random();

    public EpsilonGreedyAlgorithm(int numArms, double epsilon) {
        super(numArms);

        checkArgument(epsilon >= 0 && epsilon <= 1, "Epsilon must be between 0 and 1");
        this.epsilon = epsilon;
    }

    @Override
    public int selectArm() {
        if (random.nextDouble() > epsilon) {
            return getArmWithHigherValue();
        }

        return random.nextInt(counts.length);
    }

    private int getArmWithHigherValue() {
        ArrayList<Integer> bestArms = new ArrayList<>(values.length);
        bestArms.add(0);

        double maxValue = values[0];
        for (int i = 1; i < values.length; i++) {
            double value = values[i];
            if (value > maxValue) {
                bestArms.clear();
                bestArms.add(i);
                maxValue = value;
            } else if (value == maxValue) {
                bestArms.add(i);
            }
        }

        return bestArms.get(random.nextInt(bestArms.size()));
    }

    @Override
    public String toString() {
        return "EpsilonGreedy {epsilon=" + epsilon + "}";
    }
}
