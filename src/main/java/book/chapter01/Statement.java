package book.chapter01;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.text.NumberFormat;
import java.util.Locale;

class Statement {

    /**
     * 공연료 청구서를 반환한다.
     *
     * @param invoice 공연료 청구서
     * @param plays 공연한 연극 정보
     * @return 공연료 청구 내역에 대한 문자열
     */
    @WithSpan
    String statement(final Invoice invoice, final Plays plays) {
        return renderPlainText(createStatementData(invoice, plays));
    }

    /**
     * 공연료 청구서를 html 형식으로 반환한다.
     *
     * @param invoice 공연료 청구서
     * @param plays 공연한 연극 정보
     * @return 공연료 청구 내역에 대한 html 문자열
     */
    String htmlStatement(final Invoice invoice, final Plays plays) {
        return renderHtml(createStatementData(invoice, plays));
    }

    private StatementData createStatementData(final Invoice invoice, final Plays plays) {
        return new StatementData(invoice, plays);
    }

    private String renderPlainText(final StatementData data) {
        String result = "청구 내역 (고객명: " + data.customer() + ")\n";

        for (Performance performance : data.performances()) {
            result += data.playFor(performance).name()
                    + ": " + usd(data.amountFor(performance))
                    + " (" + performance.audience() + "석)\n";
        }
        result += "총액: " + usd(data.totalAmount()) + "\n";
        result += "적립 포인트: " + data.totalVolumeCredits() + "점\n";
        return result;
    }

    private String renderHtml(final StatementData data) {
        StringBuilder result = new StringBuilder();

        result.append(String.format("<h1>청구 내역 (고객명: %s)</h1>\n", data.customer()));
        result.append("<table>\n");
        result.append("<tr><th>연극</th><th>좌석 수</th><th>금액</th></tr>\n");

        for (Performance performance : data.performances()) {
            result.append(String.format("<tr><td>%s</td><td>%s</td><td>%d석</td></tr>\n",
                    data.playFor(performance).name(),
                    data.amountFor(performance),
                    performance.audience())
            );
        }
        result.append("</table>\n");
        result.append(String.format("<p>총액: <em>%s</em><p>\n", usd(data.totalAmount())));
        result.append(String.format("<p>적립 포인트: <em>%d</em>점</p>\n", data.totalVolumeCredits()));
        return result.toString();
    }

    private String usd(final long amount) {
        return NumberFormat.getCurrencyInstance(Locale.US).format(amount / 100);
    }
}
