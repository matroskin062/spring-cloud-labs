package com.restservice.restservice.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
@JsonDeserialize(as = Post.class)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "public_date")
    private Date publicDate = Date.from(Instant.now());

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int raiting;

    @JsonIgnore
    @Column(name = "presenceFlag", nullable = false)
    private boolean presenceFlag = true;

    public Post(String author, String title, int rating) {
        this.author = author;
        this.publicDate = Date.from(Instant.now());
        this.title = title;
        this.raiting = rating;
    }

    public Post() {
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Comment> comments;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getPublicDate() {
        return publicDate;
    }

    public void setPublicDate(Date publicDate) {
        this.publicDate = publicDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getRaiting() {
        return raiting;
    }

    public void setRaiting(int raiting) {
        this.raiting = raiting;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }


    public boolean isPresenceFlag() {
        return presenceFlag;
    }

    public void setPresenceFlag(boolean presenceFlag) {
        this.presenceFlag = presenceFlag;
    }
}
