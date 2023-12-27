package book.chapter01;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.text.NumberFormat;
import java.util.Locale;

class Statement {

    /**
     * 공연료 청구서를 출력한다.
     *
     * @return 공연료 청구 내역에 대한 문자열
     */
    @WithSpan
    String statement(final Invoice invoice, final Plays plays) {
        return renderPlainText(createStatementData(invoice, plays));
    }

    private StatementData createStatementData(final Invoice invoice, final Plays plays) {
        return new StatementData(invoice, plays);
    }

    private String renderPlainText(final StatementData data) {
        String result = "청구 내역 (고객명: " + data.customer() + ")\n";

        for (Performance performance : data.performances()) {
            // 청구 내역을 출력한다.
            result += data.playFor(performance).name()
                    + ": " + usd(data.amountFor(performance))
                    + " (" + performance.audience() + "석)\n";
        }
        result += "총액: " + usd(data.totalAmount()) + "\n";
        result += "적립 포인트: " + data.totalVolumeCredits() + "점\n";
        return result;
    }

    private String usd(final long amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }
}
