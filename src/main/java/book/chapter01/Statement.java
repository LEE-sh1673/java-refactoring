package book.chapter01;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.text.NumberFormat;
import java.util.Locale;

class Statement {

    private final Invoice invoice;

    private final Plays plays;

    public Statement(final Invoice invoice, final Plays plays) {
        this.invoice = invoice;
        this.plays = plays;
    }

    /**
     * 공연료 청구서를 출력한다.
     *
     * @return 공연료 청구 내역에 대한 문자열
     */
    @WithSpan
    public String statement() {
        long totalAmount = 0L;
        long volumnCredits = 0L;
        String result = "청구 내역 (고객명: " + invoice.customer() + ")\n";

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setMaximumFractionDigits(2);

        for (Performance performance : invoice.performances()) {
            // 포인트를 적립한다.
            volumnCredits += Math.max(performance.audience() - 30, 0L);

            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(plays.get(performance).type())) {
                volumnCredits += (long) Math.floor((double) performance.audience() / 5L);
            }

            // 청구 내역을 출력한다.
            result += plays.get(performance).name()
                    + ": " + format.format(amountFor(performance) / 100)
                    + " (" + performance.audience() + "석)\n";
            totalAmount += amountFor(performance);
        }
        result += "총액: " + format.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumnCredits + "점\n";
        return result;
    }

    private long amountFor(final Performance performance) {
        long amount = 0L;

        switch (plays.get(performance).type()) {
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
                        String.format("알 수 없는 장르: %s", plays.get(performance).type()));
        }
        return amount;
    }
}
