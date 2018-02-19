class DocumentModel {

    private int id;
    private String title;
    private String author;
    private String source;
    private String content;

    static final String ID = "id";
    static final String TITLE = "title";
    static final String AUTHOR = "author";
    static final String SOURCE = "source";
    static final String CONTENT = "content";

    DocumentModel(int id, String title, String author, String source, String content) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.source = source;
        this.content = content;
    }

    int getId() {
        return id;
    }

    String getTitle() {
        return title;
    }

    String getAuthor() {
        return author;
    }

    String getSource() {
        return source;
    }

    String getContent() {
        return content;
    }
}
