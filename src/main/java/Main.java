import java.util.ArrayList;
import java.util.Collections;

public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer();
        indexer.createIndex();

        Searcher searcher = new Searcher();
        searcher.readIndex();
        
        BaselinesModel baselines = FileParser.readBaselines();

        Evaluator evaluator = new Evaluator(searcher, baselines);
        evaluator.evaluateMAP();
        evaluator.evaluateRecall();
    }
}
