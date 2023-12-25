package book.utils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonReader {

    public String read(final String fileName) throws URISyntaxException, IOException {
        return new String(readBytes(uri(fileName)));
    }

    private URI uri(final String fileName) throws URISyntaxException {
        final URL url = this.getClass().getClassLoader().getResource(fileName);

        if (url == null) {
            throw new IllegalStateException("URL should not be null; check again.");
        }
        return url.toURI();
    }

    private static byte[] readBytes(URI uri) throws IOException {
        return Files.readAllBytes(Paths.get(uri));
    }
}
