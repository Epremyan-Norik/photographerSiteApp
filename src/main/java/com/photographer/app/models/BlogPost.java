package com.photographer.app.models;

import javax.persistence.*;

@Entity
@Table(name = "t_blogpost")
public class BlogPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title, anons, text;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    private int view;

    public BlogPost(String title, String anons, String text) {
        this.title = title;
        this.anons = anons;
        this.text = text;
    }

    public BlogPost() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public int getView() {
        return view;
    }

    public void setView(int view) {
        this.view = view;
    }
}
