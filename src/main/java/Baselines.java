import java.util.ArrayList;

class Baselines extends ArrayList<ArrayList<Integer>> {

    ArrayList<Integer> getRelevantDocuments(int id) {
        return this.get(id);
    }
}
