import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.search.similarities.*;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        System.out.print("StandardAnalyzer, VSM: ");
        runModels(new StandardAnalyzer(), new ClassicSimilarity());
        System.out.print("StandardAnalyzer, BM25: ");
        runModels(new StandardAnalyzer(), new BM25Similarity());
        System.out.print("CustomAnalyzer, VSM: ");
        runModels(new CustomAnalyzer(), new ClassicSimilarity());
        System.out.print("CustomAnalyzer, BM25: ");
        runModels(new CustomAnalyzer(), new BM25Similarity());
    }

    private static void queryOne(int id) {
        ArrayList<String> queries = FileParser.readQueries();
        Analyzer analyzer = new StandardAnalyzer();

        Indexer indexer = new Indexer();
        indexer.setAnalyzer(analyzer);
        indexer.createIndex();

        Searcher searcher = new Searcher();
        searcher.setAnalyzer(analyzer);
        searcher.readIndex();
        searcher.setSimilarity(new ClassicSimilarity());

        ArrayList<Integer> docs = searcher.search(queries.get(id), 50);
        System.out.println(docs);
    }

    private static void runModels(Analyzer analyzer, Similarity similarity) {
        Indexer indexer = new Indexer();
        indexer.setAnalyzer(analyzer);
        indexer.createIndex();
        Searcher searcher = new Searcher();
        searcher.setAnalyzer(analyzer);
        searcher.readIndex();

        Evaluator evaluator = new Evaluator(searcher);
        searcher.setSimilarity(similarity);
        evaluator.evaluateAll(false);
    }
}
