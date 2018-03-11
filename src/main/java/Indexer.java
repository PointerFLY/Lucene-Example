import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

class Indexer {

    private Analyzer analyzer = new StandardAnalyzer();

    Analyzer getAnalyzer() {
        return analyzer;
    }

    void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    void createIndex() {
        try {
            IndexWriterConfig config = new IndexWriterConfig(analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

            Directory dir = FSDirectory.open(FileUtils.INDEX_DIR);
            IndexWriter writer = new IndexWriter(dir, config);

            indexDocuments(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Index failed: " + e.toString());
            System.exit(1);
        }
    }

    private void indexDocuments(IndexWriter writer) throws IOException {
        FileParser.readDocument(document -> {
            Document luceneDoc = new Document();

            StringField id = new StringField(DocumentModel.ID, Integer.toString(document.getId()), Field.Store.YES);
            TextField title = new TextField(DocumentModel.TITLE, document.getTitle(), Field.Store.NO);
            TextField author = new TextField(DocumentModel.AUTHOR, document.getAuthor(), Field.Store.NO);
            TextField source = new TextField(DocumentModel.SOURCE, document.getSource(), Field.Store.NO);
            TextField content = new TextField(DocumentModel.CONTENT, document.getContent(), Field.Store.NO);

            luceneDoc.add(id);
            luceneDoc.add(title);
            luceneDoc.add(author);
            luceneDoc.add(source);
            luceneDoc.add(content);

            try {
                writer.addDocument(luceneDoc);
            } catch (IOException e) {
                e.printStackTrace();
                Logger.getGlobal().log(Level.SEVERE, "IndexWriter unable to addDocument: " + e.toString());
                System.exit(1);
            }
        });
    }
}
