import java.util.ArrayList;
import java.util.HashSet;

class Evaluator {

    private Searcher searcher;
    private Baselines baselines = FileParser.readBaselines();
    private ArrayList<String> queries = FileParser.readQueries();

    private static final int NUM_TOP_HITS = 50;

    Evaluator(Searcher searcher) {
        this.searcher = searcher;
    }

    void evaluateAll(boolean printEachQuery) {
        double sumAveragePrecision = 0.0;
        double sumRecall = 0.0;

        for (int i = 0; i < queries.size(); i++) {
            ArrayList<Integer> hitDocIds = searcher.search(queries.get(i), NUM_TOP_HITS);
            HashSet<Integer> standardDocIds = baselines.get(i);

            int numTruePositive = 0;
            double sumPrecision = 0.0;
            for (int j = 0; j < hitDocIds.size(); j++) {
                int hitID = hitDocIds.get(j);
                if (standardDocIds.contains(hitID)) {
                    numTruePositive++;
                    double precision = (double)numTruePositive / (j + 1);
                    sumPrecision += precision;
                }
            }

            // Per query, average precision and recall.

            double averagePrecision = 0.0;
            double recall = 0.0;
            if (standardDocIds.size() == 0) { // if standard answer is 0, set MAP and recall to 0.0
                averagePrecision = 1.0;
                recall = 1.0;
            } else {
                averagePrecision = (numTruePositive == 0 ? 0.0 : sumPrecision / numTruePositive);
                recall = (double)numTruePositive / standardDocIds.size();
            }

            sumAveragePrecision += averagePrecision;
            sumRecall += recall;

            if (printEachQuery) {
                System.out.println(String.format("Query: %d, MAP: %.4f, Recall: %.4f", i + 1, averagePrecision, recall));
            }
        }

        double meanAveragePrecision = sumAveragePrecision / queries.size();
        double meanRecall = sumRecall / queries.size();

        System.out.println(String.format("MAP: %.4f, Recall: %.4f", meanAveragePrecision, meanRecall));
    }
}
