package com.photographer.app.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "blog_post", schema = "public", catalog = "site_test")
public class BlogPost {
    private int id;
    private String title;
    private String anons;
    private String fullText;
    private int authorId;
    private Timestamp dtPost;
    private int viewsCounter;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Basic
    @Column(name = "anons")
    public String getAnons() {
        return anons;
    }

    public void setAnons(String anons) {
        this.anons = anons;
    }

    @Basic
    @Column(name = "full_text")
    public String getFullText() {
        return fullText;
    }

    public void setFullText(String fullText) {
        this.fullText = fullText;
    }

    @Basic
    @Column(name = "author_id")
    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    @Basic
    @Column(name = "dt_post")
    public Timestamp getDtPost() {
        return dtPost;
    }

    public void setDtPost(Timestamp dtPost) {
        this.dtPost = dtPost;
    }

    @Basic
    @Column(name = "views_counter")
    public int getViewsCounter() {
        return viewsCounter;
    }

    public void setViewsCounter(int viewsCounter) {
        this.viewsCounter = viewsCounter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BlogPost blogPost = (BlogPost) o;

        if (id != blogPost.id) return false;
        if (authorId != blogPost.authorId) return false;
        if (viewsCounter != blogPost.viewsCounter) return false;
        if (title != null ? !title.equals(blogPost.title) : blogPost.title != null) return false;
        if (anons != null ? !anons.equals(blogPost.anons) : blogPost.anons != null) return false;
        if (fullText != null ? !fullText.equals(blogPost.fullText) : blogPost.fullText != null) return false;
        if (dtPost != null ? !dtPost.equals(blogPost.dtPost) : blogPost.dtPost != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (anons != null ? anons.hashCode() : 0);
        result = 31 * result + (fullText != null ? fullText.hashCode() : 0);
        result = 31 * result + authorId;
        result = 31 * result + (dtPost != null ? dtPost.hashCode() : 0);
        result = 31 * result + viewsCounter;
        return result;
    }
}
