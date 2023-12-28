package book.chapter01;

class EnrichPerformance {

    private final String name;
    private final int audience;
    private final long amount;
    private final long volumeCredits;

    EnrichPerformance(
            final String name,
            final int audience,
            final long amount,
            final long volumeCredits
    ) {
        this.name = name;
        this.audience = audience;
        this.amount = amount;
        this.volumeCredits = volumeCredits;
    }

    String name() {
        return this.name;
    }

    int audience() {
        return this.audience;
    }

    long amount() {
        return this.amount;
    }

    long volumeCredits() {
        return this.volumeCredits;
    }
}
