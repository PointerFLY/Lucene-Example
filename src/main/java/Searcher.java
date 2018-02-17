import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

class Searcher {

    private Analyzer analyzer = new StandardAnalyzer();
    private IndexSearcher searcher;

    void readIndex() {
        try {
            DirectoryReader reader = DirectoryReader.open(FSDirectory.open(FileUtils.INDEX_DIR));
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Read index failed: " + e.toString());
            System.exit(1);
        }
    }

    void search(String queryStr, int num) {
        QueryParser parser = new QueryParser("content", analyzer);

        try {
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = searcher.search(query, num).scoreDocs;
        } catch (ParseException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Can't parse query: " + e.toString());
            System.exit(1);
        } catch (IOException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
