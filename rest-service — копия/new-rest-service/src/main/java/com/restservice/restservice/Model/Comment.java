package com.restservice.restservice.Model;



import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "comments")
@JsonDeserialize(as = Comment.class)
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "username", nullable = false)
    private String user_name;
    @Column(name = "comment_date")
    private Date commentDate  = Date.from(Instant.now());
    @Column(name = "rating", nullable = false)
    private int rating;

    @JsonIgnore
    @Column(name = "presenceFlag", nullable = false)
    private boolean presenceFlag = true;

    public Comment() {
    }

    public Comment(Post post, String user_name, int rating) {
        this.post = post;
        this.user_name = user_name;
        this.commentDate = Date.from(Instant.now());
        this.rating = rating;
    }

    @ManyToOne
    @JoinColumn(name = "postID")
    @JsonProperty("post")
    private Post post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public boolean isPresenceFlag() {
        return presenceFlag;
    }

    public void setPresenceFlag(boolean presenceFlag) {
        this.presenceFlag = presenceFlag;
    }
}
