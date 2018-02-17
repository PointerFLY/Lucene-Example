import java.util.ArrayList;

class Baselines extends ArrayList<ArrayList<Integer>> {

    ArrayList<Integer> getRelevantDocumentIds(int queryId) {
        return this.get(queryId);
    }
}
