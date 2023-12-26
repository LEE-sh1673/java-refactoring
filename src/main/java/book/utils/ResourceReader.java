package book.utils;

import io.opentelemetry.instrumentation.annotations.WithSpan;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;

public final class ResourceReader {

    private static final String BASE_PATH
            = String.join(File.separator, "src", "test", "resources");

    private static final String INVALID_FILE_NAME = "\"%s\"(은)는 유효하지 않은 파일 이름입니다.";
    private static final String FILE_NOT_FOUND = "\"%s\"라는 이름의 파일을 찾을 수 없습니다.";
    private static final String IO_EXCEPTION_OCCURRED = "I/O exception of some sort has occurred.";

    @WithSpan
    public static String read(final String fileName) {
        return new String(readBytes(stream(toFile(fileName))));
    }

    private static File toFile(final String fileName) {
        if (StringUtils.isBlank(fileName)) {
            throw new IllegalArgumentException(String.format(INVALID_FILE_NAME, fileName));
        }
        return Paths.get(BASE_PATH, fileName).toAbsolutePath().toFile();
    }

    private static InputStream stream(final File file) {
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new IllegalStateException(String.format(FILE_NOT_FOUND, file.getName()));
        }
    }

    private static byte[] readBytes(final InputStream in) {
        try {
            return in.readAllBytes();
        } catch (IOException e) {
            throw new IllegalStateException(IO_EXCEPTION_OCCURRED);
        }
    }
}
