import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

class FileParser {

    @FunctionalInterface
    interface DocumentProcessor {
        void process(DocumentModel model);
    }

    static void readDocument(DocumentProcessor processor) {
        DocumentModel model = new DocumentModel(1, "", "", "", "");
        processor.process(model);
    }

    static ArrayList<String> readQueries() {
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

    static Baselines readBaselines() {
        Baselines baselines = new Baselines();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(FileUtils.BASELINE_FILE.toFile()));
            String line;
            int oldQueryId = 1;
            ArrayList<Integer> list = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
                String[] items = line.split("\\s+");
                int queryId = Integer.parseInt(items[0]);
                int documentId = Integer.parseInt(items[1]);
                int relevance = Integer.parseInt(items[2]);

                // New queryId starts or not
                if (queryId != oldQueryId) {
                    oldQueryId = queryId;
                    baselines.add(list);
                    list = new ArrayList<>();
                }
                if (relevance >= 1 && relevance <= 3) {
                   list.add(documentId);
                }
            }
            // last queryId
            baselines.add(list);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getGlobal().log(Level.SEVERE, "Read baseline file failed: " + e.toString());
            System.exit(1);
        }

        return baselines;
    }
}
