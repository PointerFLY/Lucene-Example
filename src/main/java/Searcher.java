import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Searcher {

    private Analyzer analyzer = new StandardAnalyzer();
    private IndexSearcher searcher;

    Analyzer getAnalyzer() {
        return analyzer;
    }

    void setAnalyzer(Analyzer analyzer) {
        this.analyzer = analyzer;
    }

    void setSimilarity(Similarity similarity) {
        searcher.setSimilarity(similarity);
    }

    void readIndex() {
        try {
            DirectoryReader reader = DirectoryReader.open(FSDirectory.open(FileUtils.INDEX_DIR));
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Read index failed");
            System.exit(1);
        }
    }

    ArrayList<Integer> search(String queryStr, int topHitsCount) {
        String fields[] = new String[] { DocumentModel.TITLE, DocumentModel.AUTHOR, DocumentModel.SOURCE, DocumentModel.CONTENT };
        QueryParser parser = new MultiFieldQueryParser(fields, analyzer);

        try {
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = searcher.search(query, topHitsCount).scoreDocs;
            ArrayList<Integer> docIds = new ArrayList<>();
            for (ScoreDoc hit: hits) {
                Document doc = searcher.doc(hit.doc);
                int id = Integer.parseInt(doc.get("id"));
                docIds.add(id);
            }
            return docIds;
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Can't parse query");
            System.exit(1);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }

        return null;
    }
}
