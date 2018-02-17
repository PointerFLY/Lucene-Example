import java.util.ArrayList;

class BaselinesModel extends ArrayList<ArrayList<Integer>> {

    public ArrayList<Integer> getRelevantDocuments(int id) {
        return this.get(id);
    }
}
