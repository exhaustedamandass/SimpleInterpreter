package reader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SourceReader {
    private final String filePath;

    public SourceReader(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads all text from the file and returns it as a single string.
     */
    public String read() throws IOException {
        return new String(Files.readAllBytes(Paths.get(filePath)));
    }
}
