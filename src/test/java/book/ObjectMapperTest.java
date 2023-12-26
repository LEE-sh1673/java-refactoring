package book;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import static org.assertj.core.api.Assertions.assertThat;

import book.utils.JsonReader;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ObjectMapperTest {

    private static final ObjectMapper mapper = new ObjectMapper();

    @BeforeAll
    static void tearUp() {
        mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    @Test
    void test_deserialization() throws IOException {
        // given
        final String contents = JsonReader.read("user.json");

        // when
        final User user = mapper.readValue(contents, User.class);

        // then
        assertThat(user.getName()).isEqualTo("lsh");
        assertThat(user.getAge()).isEqualTo(26);
    }

    @Test
    void test_serialization() throws JsonProcessingException {
        // given
        final User user = new User("lee seung hoon", 26);

        // when
        final String contents = mapper.writeValueAsString(user);

        // then
        assertThat(contents).isEqualTo("{\"name\":\"lee seung hoon\",\"age\":26}");
    }

    static class User {

        private final String name;
        private final int age;

        User() {
            this("UNKNOWN", 0);
        }

        User(final String name, final int age) {
            this.name = name;
            this.age = age;
        }

        String getName() {
            return name;
        }

        int getAge() {
            return age;
        }
    }
}
