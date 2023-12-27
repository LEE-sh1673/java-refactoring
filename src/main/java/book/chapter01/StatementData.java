package book.chapter01;

class StatementData {

    private final Invoice invoice;
    private final Plays plays;

    StatementData(final Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    String customer() {
        return invoice.customer();
    }

    Iterable<Performance> performances() {
        return invoice.performances();
    }

    Play playFor(final Performance performance) {
        return plays.findById(performance.playID());
    }

    long amountFor(final Performance performance) {
        long amount = 0L;

        switch (playFor(performance).type()) {
            case "tragedy":  // 비극
                amount = 40_000L;
                if (performance.audience() > 30) {
                    amount += 1000L * (performance.audience() - 30);
                }
                break;
            case "comedy":  // 희극
                amount = 30_000L;
                if (performance.audience() > 20) {
                    amount += 10000L + 500L * (performance.audience() - 20);
                }
                amount += 300L * performance.audience();
                break;
            default:
                throw new RuntimeException(
                        String.format("알 수 없는 장르: %s", playFor(performance).type())
                );
        }
        return amount;
    }

    long totalAmount() {
        long totalAmount = 0L;
        for (Performance performance : invoice.performances()) {
            totalAmount += amountFor(performance);
        }
        return totalAmount;
    }

    long totalVolumeCredits() {
        long volumnCredits = 0L;
        for (Performance performance : invoice.performances()) {
            volumnCredits += volumeCreditsFor(performance);
        }
        return volumnCredits;
    }

    private long volumeCreditsFor(final Performance performance) {
        long volumnCredits = Math.max(performance.audience() - 30, 0L);

        if ("comedy".equals(playFor(performance).type())) {
            volumnCredits += Math.floorDiv(performance.audience(), 5);
        }
        return volumnCredits;
    }
}
