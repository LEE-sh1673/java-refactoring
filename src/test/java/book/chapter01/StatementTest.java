package book.chapter01;

import static org.assertj.core.api.Assertions.assertThat;

import book.utils.JsonReader;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StatementTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void tearUp() {
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY)
                .registerModule(new SimpleModule()
                        .addDeserializer(Plays.class, new PlaysDeserializer())
                );
    }

    @Test
    void test_statement() throws JsonProcessingException {
        // given
        final Invoice invoice = mapper.readValue(
                JsonReader.read("invoice.json"),
                Invoice.class
        );
        final Plays plays = mapper.readValue(
                JsonReader.read("plays.json"),
                Plays.class
        );

        // when
        final Statement statement = new Statement();
        final String result = statement.statement(invoice, plays);

        // then
        assertThat(result).isEqualTo("""
                청구 내역 (고객명: BigCo)
                Hamlet: $650.00 (55석)
                As You Like It: $580.00 (35석)
                Othello: $500.00 (40석)
                총액: $1,730.00
                적립 포인트: 47점
                """);
    }
}
