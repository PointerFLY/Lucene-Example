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
        double sumMAP = 0.0;
        double sumRecall = 0.0;

        for (int i = 0; i < queries.size(); i++) {
            ArrayList<Integer> hitDocIds = searcher.search(queries.get(i), NUM_TOP_HITS);
            HashSet<Integer> standardDocIds = baselines.get(i);

            int numTruePositive = 0;
            double averagePrecision = 0.0;
            double averagePrecisionSum = 0.0;
            for (int j = 0; j < hitDocIds.size(); j++) {
                int hitID = hitDocIds.get(j);
                if (standardDocIds.contains(hitID)) {
                    numTruePositive++;
                    averagePrecision = (double)numTruePositive / (j + 1);
                    averagePrecisionSum += averagePrecision;
                }
            }

            double meanAveragePrecision = 0.0;
            double recall = 0.0;
            if (standardDocIds.size() == 0) {
                meanAveragePrecision = 1.0;
                recall = 1.0;
            } else {
                meanAveragePrecision = (numTruePositive == 0 ? 0.0 : averagePrecisionSum / numTruePositive);
                recall = (double)numTruePositive / standardDocIds.size();
            }

            sumMAP += meanAveragePrecision;
            sumRecall += recall;

            if (printEachQuery) {
                System.out.println(String.format("Query: %d, MAP: %.4f, Recall: %.4f", i + 1, meanAveragePrecision, recall));
            }
        }

        double averageMAP = sumMAP / queries.size();
        double averageRecall = sumRecall / queries.size();

        System.out.println(String.format("Average MAP: %.4f, Average Recall: %.4f", averageMAP, averageRecall));
    }
}
