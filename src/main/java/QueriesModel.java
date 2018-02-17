import java.util.ArrayList;

class QueriesModel extends ArrayList<String> {

    public String getQuery(int id) {
        return this.get(id);
    }
}
