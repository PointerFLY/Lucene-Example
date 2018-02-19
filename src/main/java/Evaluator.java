import java.util.ArrayList;

class Evaluator {

    private Searcher searcher;
    private Baselines baselines = FileParser.readBaselines();
    private ArrayList<String> queries = FileParser.readQueries();

    Evaluator(Searcher searcher) {
        this.searcher = searcher;
    }

    void evaluateAll(boolean printEachQuery) {
        double sumMAP = 0.0;
        double sumRecall = 0.0;

        for (int i = 0; i < queries.size(); i++) {
            ArrayList<Integer> hitDocIds = searcher.search(queries.get(i));
            ArrayList<Integer> standardDocIds = baselines.get(i);

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

            double meanAveragePrecision = (numTruePositive == 0 ? 0.0 : averagePrecisionSum / numTruePositive);
            double recall = (standardDocIds.size() == 0 ? 0.0 : (double)numTruePositive / standardDocIds.size());

            sumMAP += meanAveragePrecision;
            sumRecall += recall;

            if (printEachQuery) {
                System.out.println(String.format("Query: %d, MAP: %.2f, Recall: %.2f", i + 1, meanAveragePrecision, recall));
            }
        }

        double averageMAP = sumMAP / queries.size();
        double averageRecall = sumRecall / queries.size();

        System.out.println(String.format("Average MAP: %.2f, Average Recall: %.2f", averageMAP, averageRecall));
    }
}
