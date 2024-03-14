package com.example.Triple_clone.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String local;
    private String name;
    private double score;
    private String category;
    private int lentTime;
    private long lentPrice;
    private boolean lentStatus;
    private LocalTime enterTime;
    private long discountRate;
    private long originPrice;
    private long totalPrice;

    @Builder
    public Accommodation(String local, String name, double score, String category, int lentTime, long lentPrice, boolean lentStatus, String enterTime, long discountRate, long originPrice, long totalPrice) {
        this.local = local;
        this.name = name;
        this.score = score;
        this.category = category;
        this.lentTime = lentTime;
        this.lentPrice = lentPrice;
        this.lentStatus = lentStatus;
        this.enterTime = null;
        if (enterTime != null) {
            this.enterTime = LocalTime.parse(enterTime, DateTimeFormatter.ofPattern("HH:mm"));
        }
        this.discountRate = discountRate;
        this.originPrice = originPrice;
        this.totalPrice = totalPrice;
    }
}