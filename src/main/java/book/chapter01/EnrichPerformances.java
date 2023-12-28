package book.chapter01;

import java.util.List;

class EnrichPerformances {

    private final List<EnrichPerformance> performances;

    EnrichPerformances(final Invoice invoice, final Plays plays) {
        this.performances = invoice.performances().stream()
                .map(performance -> enrichPerformance(
                        plays.findById(performance.playID()), performance
                ))
                .toList();
    }

    private EnrichPerformance enrichPerformance(
            final Play play,
            final Performance performance
    ) {
        final PerformanceCalculator calculator
                = PerformanceCalculatorFactory.createCalculator(performance, play);

        return new EnrichPerformance(
                play.name(),
                performance.audience(),
                calculator.amount(),
                calculator.volumeCredits()
        );
    }

    Iterable<EnrichPerformance> performances() {
        return performances;
    }

    long totalAmount() {
        return performances.stream()
                .mapToLong(EnrichPerformance::amount)
                .sum();
    }

    long totalVolumeCredits() {
        return performances.stream()
                .mapToLong(EnrichPerformance::volumeCredits)
                .sum();
    }
}
