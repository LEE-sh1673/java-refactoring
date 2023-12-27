package book.chapter01;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.text.NumberFormat;
import java.util.Locale;

class Statement {

    private final Invoice invoice;
    private final Plays plays;

    Statement(final Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * 공연료 청구서를 출력한다.
     *
     * @return 공연료 청구 내역에 대한 문자열
     */
    @WithSpan
    String statement() {
        long totalAmount = 0L;
        String result = "청구 내역 (고객명: " + invoice.customer() + ")\n";

        for (Performance performance : invoice.performances()) {
            // 청구 내역을 출력한다.
            result += playFor(performance).name()
                    + ": " + usd(amountFor(performance))
                    + " (" + performance.audience() + "석)\n";
            totalAmount += amountFor(performance);
        }
        result += "총액: " + usd(totalAmount) + "\n";
        result += "적립 포인트: " + totalVolumeCredits() + "점\n";
        return result;
    }

    private Play playFor(final Performance performance) {
        return plays.findById(performance.playID());
    }

    private long amountFor(final Performance performance) {
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

    private long totalVolumeCredits() {
        long volumnCredits = 0L;
        for (Performance performance : invoice.performances()) {
            volumnCredits += volumeCreditsFor(performance);
        }
        return volumnCredits;
    }

    private long volumeCreditsFor(final Performance performance) {
        long volumnCredits = Math.max(performance.audience() - 30, 0L);

        if ("comedy".equals(playFor(performance).type())) {
            volumnCredits += (long) Math.floor((double) performance.audience() / 5L);
        }
        return volumnCredits;
    }

    private String usd(final long amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }
}
