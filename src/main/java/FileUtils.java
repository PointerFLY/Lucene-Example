import org.codehaus.plexus.archiver.tar.TarGZipUnArchiver;
import org.codehaus.plexus.logging.console.ConsoleLoggerManager;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {
    public static final String TEMP_DIR = "temp/";
    public static final String INDEX_DIR = TEMP_DIR.concat("index/");
    public static final String DOCS_DIR = TEMP_DIR.concat("docs/");

    private static final String DOCS_URL = "http://ir.dcs.gla.ac.uk/resources/test_collections/cran/cran.tar.gz";

    public static void initialize() {
        createDirectory(INDEX_DIR);
        createDirectory(DOCS_DIR);
        String gzipFile = fetchDocs(DOCS_URL, TEMP_DIR);
        decompress(gzipFile, DOCS_DIR);
    }

    private static void decompress(String gzipFile, String dir) {
        final TarGZipUnArchiver unarchiver = new TarGZipUnArchiver();
        ConsoleLoggerManager manager = new ConsoleLoggerManager();
        manager.initialize();
        unarchiver.enableLogging(manager.getLoggerForComponent(null));
        unarchiver.setSourceFile(new File(gzipFile));
        unarchiver.setDestDirectory(new File(dir));
        unarchiver.extract();
    }

    private static void createDirectory(String path) {
        Path dir = Paths.get(path);
        if (Files.notExists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    private static String fetchDocs(String urlStr, String toDir) {
        try {
            int index = urlStr.lastIndexOf('/');
            String fileName = urlStr.substring(index + 1);
            Path path = Paths.get(toDir + fileName);

            if (Files.notExists(path)) {
                URL url = new URL(urlStr);
                InputStream in = url.openStream();
                Files.copy(in, path);
            }
            return path.toString();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}