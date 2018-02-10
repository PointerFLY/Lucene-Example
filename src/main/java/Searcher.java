import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.store.FSDirectory;
import java.io.IOException;

public class Searcher {

    private Analyzer analyzer = new StandardAnalyzer();
    private IndexSearcher searcher;

    public Searcher() {

    }

    public void readIndex() {

        try {
            DirectoryReader reader = DirectoryReader.open(FSDirectory.open(FileUtils.INDEX_DIR));
            searcher = new IndexSearcher(reader);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void search(String queryStr) {
        QueryParser parser = new QueryParser("content", analyzer);

        try {
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = searcher.search(query, 5).scoreDocs;
            // Iterate through the results.
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = searcher.doc(hits[i].doc);
                String idStr = hitDoc.get("id");
                System.out.println(idStr);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
