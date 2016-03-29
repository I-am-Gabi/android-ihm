package polynews.polytech.unice.fr.polynews.model;

/**
 * Class to represents the article model.
 * @author Gabriela Cavalcante
 * @version 28/03/16.
 */
public class News {
    private int id;
    private String title;
    private String content;
    private String author;
    private String date;
    private int category;
    private int media_type;
    private String media_path;

    public News() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public void setMedia_type(int media_type) {
        this.media_type = media_type;
    }

    public String getMedia_path() {
        return media_path;
    }

    public void setMedia_path(String media_path) {
        this.media_path = media_path;
    }
}
