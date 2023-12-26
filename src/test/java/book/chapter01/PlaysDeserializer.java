package book.chapter01;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Map;

class PlaysDeserializer extends StdDeserializer<Plays> {

    private final ObjectMapper mapper;

    PlaysDeserializer() {
        this(null);
    }

    PlaysDeserializer(final Class<?> cls) {
        super(cls);
        this.mapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    @Override
    public Plays deserialize(
            final JsonParser jsonParser,
            final DeserializationContext context
    ) throws IOException {
        return new Plays(readValue(jsonParser));
    }

    private Map<String, Play> readValue(final JsonParser jsonParser) throws IOException {
        return mapper.readValue(jsonParser, new TypeReference<>() {
        });
    }
}
