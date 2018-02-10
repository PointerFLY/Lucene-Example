import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class FileParser {

    public static ArrayList<String> readQueries() {
        ArrayList<String> queries = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileUtils.QUERY_FILE.toFile()));
            StringBuilder builder = new StringBuilder();
            String line;
            // TODO: Failure tolerance
            while ((line = reader.readLine()) != null) {
                String identity = line.substring(0, 2);
                switch (identity) {
                    case ".I": {
                        continue;
                    }
                    case ".W": {
                        if (builder.length() != 0) {
                            String queryStr = builder.toString();
                            queries.add(queryStr);
                            builder.setLength(0);
                        }
                        break;
                    }
                    default: {
                        builder.append(line);
                        break;
                    }
                }
            }
            queries.add(builder.toString());
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return queries;
    }

    public static ArrayList<ArrayList<Integer>> readBaselines() {
        ArrayList<ArrayList<Integer>> baselines = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileUtils.BASELINE_FILE.toFile()));
            String line;
            int oldQueryId = 1;
            ArrayList<Integer> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                int queryIdx = line.indexOf(' ');
                int queryId = Integer.parseInt(line.substring(0, queryIdx));
                int docIdx = line.indexOf(' ', queryIdx + 1);
                int docId = Integer.parseInt(line.substring(queryIdx + 1, docIdx));

                if (queryId != oldQueryId) {
                    oldQueryId = queryId;
                    baselines.add(list);
                    list = new ArrayList<>();
                }
                list.add(docId);
            }
            baselines.add(list);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        return baselines;
    }
}
