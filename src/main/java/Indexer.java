import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class Indexer {

    private Path docsDir;
    private Path indexDir;

    public Indexer(Path docsDir, Path indexDir) {
        this.docsDir = docsDir;
        this.indexDir = indexDir;
    }

    public void createIndex() {
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + indexDir + "'...");

            Directory dir = FSDirectory.open(indexDir);
            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);

            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            IndexWriter writer = new IndexWriter(dir, config);
            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void indexDocs(IndexWriter writer, Path path) {

    }
}
