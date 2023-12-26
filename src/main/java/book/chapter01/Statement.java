package book.chapter01;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.text.NumberFormat;
import java.util.Locale;

class Statement {

    /**
     * 공연료 청구서를 출력한다.
     *
     * @param invoice 공연료 청구서
     * @param plays   공연한 연극 정보
     * @return 공연료 청구 내역에 대한 문자열
     */
    @WithSpan
    public String statement(final Invoice invoice, final Plays plays) {
        long totalAmount = 0L;
        long volumnCredits = 0L;
        String result = "청구 내역 (고객명: " + invoice.customer() + ")\n";

        NumberFormat format = NumberFormat.getCurrencyInstance(Locale.US);
        format.setMaximumFractionDigits(2);

        for (Performance performance : invoice.performances()) {
            Play play = plays.findById(performance.playID());
            long amount;

            switch (play.type()) {
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
                    throw new RuntimeException(String.format("알 수 없는 장르: %s", play.type()));
            }

            // 포인트를 적립한다.
            volumnCredits += Math.max(performance.audience() - 30, 0L);

            // 희극 관객 5명마다 추가 포인트를 제공한다.
            if ("comedy".equals(play.type())) {
                volumnCredits += (long) Math.floor((double) performance.audience() / 5L);
            }

            // 청구 내역을 출력한다.
            result += play.name()
                    + ": " + format.format(amount / 100)
                    + " (" + performance.audience() + "석)\n";
            totalAmount += amount;
        }
        result += "총액: " + format.format(totalAmount / 100) + "\n";
        result += "적립 포인트: " + volumnCredits + "점\n";
        return result;
    }
}
