package com.example.Triple_clone.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @ManyToOne
    private User user;

    @ManyToOne
    private Place place;

    private String content;
    private String image;

    public Review(User user, Place place, String content, String image) {
        this.user = user;
        this.place = place;
        this.content = content;
        this.image = image;
    }
}
