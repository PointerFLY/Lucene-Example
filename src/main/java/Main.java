public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer();
        indexer.createIndex();

        Searcher searcher = new Searcher();
        searcher.readIndex();
    }
}
