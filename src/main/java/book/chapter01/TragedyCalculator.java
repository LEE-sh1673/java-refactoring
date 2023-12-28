package book.chapter01;

class TragedyCalculator extends PerformanceCalculator {

    TragedyCalculator(final Performance performance) {
        super(performance);
    }

    @Override
    long amount() {
        long amount = 40_000L;

        if (performance.audience() > 30) {
            amount += 1000L * (performance.audience() - 30);
        }
        return amount;
    }

    @Override
    long volumeCredits() {
        return Math.max(performance.audience() - 30, 0L);
    }
}
