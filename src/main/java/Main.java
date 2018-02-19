import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.BM25Similarity;
import org.apache.lucene.search.similarities.BooleanSimilarity;
import org.apache.lucene.search.similarities.ClassicSimilarity;
import org.apache.lucene.search.similarities.MultiSimilarity;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer();
        indexer.setAnalyzer(new StandardAnalyzer());
        indexer.createIndex();
        Searcher searcher = new Searcher();
        searcher.setAnalyzer(new StandardAnalyzer());
        searcher.readIndex();

        Evaluator evaluator = new Evaluator(searcher);

        // BM25 Model
        searcher.setSimilarity(new BM25Similarity());
        evaluator.evaluateAll(false);

        // Vector Space Model
        searcher.setSimilarity(new ClassicSimilarity());
        evaluator.evaluateAll(false);
    }
}
