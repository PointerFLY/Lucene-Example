import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.nio.file.Path;
import java.util.Date;

public class Indexer {

    public Indexer() {

    }

    public void createIndex() {
        Date start = new Date();
        try {
            System.out.println("Indexing to directory '" + FileUtils.INDEX_DIR + "'...");

            Analyzer analyzer = new StandardAnalyzer();
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            Directory dir = FSDirectory.open(FileUtils.INDEX_DIR);
            IndexWriter writer = new IndexWriter(dir, config);

            indexDocs(writer, FileUtils.DOCS_FILE);
            writer.close();

            Date end = new Date();
            System.out.println(end.getTime() - start.getTime() + " total milliseconds");

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void indexDocs(IndexWriter writer, Path path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

        Document doc = new Document();
        StringBuilder builder = new StringBuilder();
        String line;
        // TODO: Failure tolerance, Now assume order: [".I", ".T", ".A", ".B", ".W"]
        while ((line = reader.readLine()) != null) {
            String identity = line.substring(0, 2);
            switch (identity) {
                case ".I": {
                    if (builder.length() != 0) {
                        writer.addDocument(doc);
                        System.out.println("writer adds document");
                    }
                    doc = new Document();
                    StringField field = new StringField("id", line.substring(2, 3), Field.Store.YES);
                    doc.add(field);
                    break;
                }
                case ".T": {
                    TextField field = new TextField("content", builder.toString(), Field.Store.YES);
                    builder.setLength(0);
                    doc.add(field);
                    break;
                }
                case ".A": {
                    TextField field = new TextField("title", builder.toString(), Field.Store.YES);
                    builder.setLength(0);
                    doc.add(field);
                    break;
                }
                case ".B": {
                    TextField field = new TextField("author", builder.toString(), Field.Store.YES);
                    builder.setLength(0);
                    doc.add(field);
                    break;
                }
                case ".W": {
                    TextField field = new TextField("bibliography", builder.toString(), Field.Store.YES);
                    builder.setLength(0);
                    doc.add(field);
                    break;
                }
                default: {
                    builder.append(line);
                    break;
                }
            }
        }

        reader.close();
    }
}
