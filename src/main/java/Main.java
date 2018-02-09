public class Main {

    public static void main(String[] args) {
        FileUtils.initialize();

        Indexer indexer = new Indexer(FileUtils.DOCS_DIR, FileUtils.INDEX_DIR);
        indexer.createIndex();

        Searcher searcher = new Searcher(FileUtils.INDEX_DIR);
        searcher.readIndex();
    }
}
