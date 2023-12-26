package book.utils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

class ResourceReaderTest {

    @Test
    void test_read() {
        assertThat(ResourceReader.read("money.json")).contains("amount");
        assertThat(ResourceReader.read("user.json")).contains("name", "age");
    }

    @ParameterizedTest
    @NullAndEmptySource
    void test_read_when_file_null_or_empty(final String fileName) {
        assertThatThrownBy(() -> ResourceReader.read(fileName))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void test_read_when_file_non_exists() {
        assertThatThrownBy(() -> ResourceReader.read("non-exists.json"))
                .isInstanceOf(IllegalStateException.class);
    }
}
