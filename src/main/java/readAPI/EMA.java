package readAPI;

import org.apache.commons.math3.stat.StatUtils;

public class EMA {
    private int period;

    public EMA(final int period) {
        this.period = period;
    }

    public double count(final double[] prizes, final int offset, final double[] emas) {
        if (prizes.length - offset <= period) {
            final double[] startPrizes = new double[period];
            System.arraycopy(prizes, offset, startPrizes, 0, period);
            return StatUtils.mean(startPrizes, 0, period);
        } else {
            final double previousEma = count(prizes, offset + 1, emas);
            final double a = (double) 2 / (period + 1);
            final double actualEma = (prizes[offset] - previousEma) * a + previousEma;
            emas[offset] = actualEma;
            return actualEma;
        }
    }
}