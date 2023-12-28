package book.chapter01;

class ComedyCalculator extends PerformanceCalculator {

    ComedyCalculator(final Performance performance) {
        super(performance);
    }

    @Override
    long amount() {
        long amount = 30_000L;

        if (performance.audience() > 20) {
            amount += 10000L + 500L * (performance.audience() - 20);
        }
        amount += 300L * performance.audience();
        return amount;
    }

    @Override
    long volumeCredits() {
        long volumnCredits = Math.max(performance.audience() - 30, 0L);
        volumnCredits += Math.floorDiv(performance.audience(), 5);
        return volumnCredits;
    }
}
