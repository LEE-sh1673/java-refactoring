package book.chapter01;

import book.chapter01.Play;
import book.chapter01.Plays;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class PlaysDeserializer extends StdDeserializer<Plays> {

    private final ObjectMapper mapper;

    public PlaysDeserializer() {
        this(null);
    }

    protected PlaysDeserializer(final Class<?> cls) {
        super(cls);
        this.mapper = new ObjectMapper()
                .setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    }

    @Override
    public Plays deserialize(
            final JsonParser jsonParser,
            final DeserializationContext context
    ) throws IOException {
        final Iterator<Entry<String, JsonNode>> fields = fields(jsonParser);
        final Map<String, Play> plays = new HashMap<>();

        while (fields.hasNext()) {
            final Map.Entry<String, JsonNode> entry = fields.next();
            final Play play = mapper.convertValue(entry.getValue(), Play.class);
            plays.put(entry.getKey(), play);
        }
        return new Plays(plays);
    }

    Iterator<Entry<String, JsonNode>> fields(final JsonParser parser) throws IOException {
        final JsonNode node = parser.getCodec().readTree(parser);
        return node.fields();
    }
}
