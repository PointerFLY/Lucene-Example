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
import java.util.ArrayList;
import java.util.Collections;

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

    public void search(String queryStr, int num) {
        QueryParser parser = new QueryParser("content", analyzer);

        try {
            Query query = parser.parse(queryStr);
            ScoreDoc[] hits = searcher.search(query, num).scoreDocs;
            // Iterate through the results.
            ArrayList<Integer> ids = new ArrayList<>();
            for (int i = 0; i < hits.length; i++) {
                Document hitDoc = searcher.doc(hits[i].doc);
                int idStr = Integer.parseInt(hitDoc.get("id"));
                ids.add(idStr);
            }
            Collections.sort(ids);
            System.out.println(ids);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
