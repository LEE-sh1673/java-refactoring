package book.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class JsonReaderTest {

    @Test
    void test_read() {
        assertThat(JsonReader.read("money.json")).contains("amount");
        assertThat(JsonReader.read("user.json")).contains("name", "age");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void test_read_when_file_null_or_empty(final String fileName) {
        assertThatThrownBy(() -> JsonReader.read(fileName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void test_read_when_file_non_exists() {
        assertThatThrownBy(() -> JsonReader.read("non-exists.json"))
                .isInstanceOf(IllegalStateException.class);
    }
}
