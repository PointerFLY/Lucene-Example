import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

class Evaluator {

    private Searcher searcher;
    private Baselines baselines = FileParser.readBaselines();
    ArrayList<String> queries = FileParser.readQueries();

    Evaluator(Searcher searcher) {
        this.searcher = searcher;
    }

    void evaluate(int queryId) {
        ArrayList<Integer> hitDocIds = searcher.search(queries.get(queryId));
        ArrayList<Integer> standardDocIds = baselines.get(queryId);

        int numTruePositive = 0;
        double averagePrecision = 0.0;
        double averagePrecisionSum = 0.0;
        for (int i = 0; i < hitDocIds.size(); i++) {
            int hitID = hitDocIds.get(i);
            if (standardDocIds.contains(hitID)) {
                numTruePositive++;
                averagePrecision = (double)numTruePositive / (i + 1);
                averagePrecisionSum += averagePrecision;
            }
        }

        double meanAveragePrecision = (numTruePositive == 0 ? 0.0 : averagePrecisionSum / numTruePositive);
        double recall = (standardDocIds.size() == 0 ? 0.0 : (double)numTruePositive / standardDocIds.size());

        System.out.println(String.format("Question: %d, Mean Average Precision: %.2f, Recall: %.2f", queryId + 1, meanAveragePrecision, recall));
    }

    void evaluateAll() {
        for (int i = 0; i < queries.size(); i++) {
            evaluate(i);
        }
    }
}
