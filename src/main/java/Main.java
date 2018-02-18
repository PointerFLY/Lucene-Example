import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer();
        indexer.createIndex();

        Searcher searcher = new Searcher();
        searcher.readIndex();
        
        Baselines baselines = FileParser.readBaselines();
        ArrayList<String> queries = FileParser.readQueries();

        Evaluator evaluator = new Evaluator(searcher);
        evaluator.evaluate(0);
        evaluator.evaluateAll();
    }
}
