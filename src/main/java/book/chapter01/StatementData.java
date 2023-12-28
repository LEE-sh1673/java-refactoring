package book.chapter01;

class StatementData {

    private final String customer;
    private final EnrichPerformances performances;

    StatementData(final Invoice invoice, final Plays plays) {
        this.customer = invoice.customer();
        this.performances = new EnrichPerformances(invoice, plays);
    }

    String customer() {
        return customer;
    }

    Iterable<EnrichPerformance> performances() {
        return performances.performances();
    }

    long totalAmount() {
        return performances.totalAmount();
    }

    long totalVolumeCredits() {
        return performances.totalVolumeCredits();
    }
}
