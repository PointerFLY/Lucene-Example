class DocumentModel {

    private int id;
    private String title;
    private String author;
    private String source;
    private String content;

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
