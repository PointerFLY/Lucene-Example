import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer();
        indexer.createIndex();

        Searcher searcher = new Searcher();
        searcher.readIndex();

        ArrayList<ArrayList<Integer>> baselines = FileParser.readBaselines();
        ArrayList<String> queries = FileParser.readQueries();
        int idx = 0;
        searcher.search(queries.get(idx), 30);
        Collections.sort(baselines.get(idx));
        System.out.println(baselines.get(idx));
    }
}
