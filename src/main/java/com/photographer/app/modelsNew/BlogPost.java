package com.photographer.app.modelsNew;


public class BlogPost {

    private long id;

    private String title, anons, text;

    private String author;

    private String dateTime;

    private int view;


    public BlogPost(String title, String anons, String text, String author) {
        this.author = author;
        this.title = title;
        this.anons = anons;
        this.text = text;
    }

    public BlogPost() {
    }


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "\nBlogPost{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", anons='" + anons + '\'' +
                ", text='" + text + '\'' +
                ", author='" + author + '\'' +
                ", dateTime='" + dateTime + '\'' +
                ", view=" + view +
                '}';
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }

    public static BlogPost incrementView(BlogPost post){
        int views = post.getView();
        views++;
        post.setView(views);
        return post;

    }
}
