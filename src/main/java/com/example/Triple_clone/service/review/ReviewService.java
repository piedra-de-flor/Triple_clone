package com.example.Triple_clone.service.review;

import com.example.Triple_clone.domain.entity.Review;
import com.example.Triple_clone.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReviewService {
    private final ReviewRepository repository;

    public void save(Review review) {
        repository.save(review);
    }
}
