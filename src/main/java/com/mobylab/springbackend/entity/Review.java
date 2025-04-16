package com.mobylab.springbackend.entity;

import jakarta.persistence.*;

@Entity
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int rating;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    private Equipment equipment;

    // getters & setters
    public Long getId() {
        return id;
    }
    public Review setId(Long id) {
        this.id = id;
        return this;
    }
    public int getRating() {
        return rating;
    }
    public Review setRating(int rating) {
        this.rating = rating;
        return this;
    }
    public String getComment() {
        return comment;
    }
    public Review setComment(String comment) {
        this.comment = comment;
        return this;
    }

}
