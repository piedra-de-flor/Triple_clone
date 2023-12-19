package com.example.Triple_clone.service.recommend.user;

import com.example.Triple_clone.dto.recommend.user.RecommendReadDto;
import com.example.Triple_clone.dto.recommend.user.RecommendWriteReviewDto;
import com.example.Triple_clone.entity.Place;
import com.example.Triple_clone.entity.Review;
import com.example.Triple_clone.entity.User;
import com.example.Triple_clone.repository.PlaceRepository;
import com.example.Triple_clone.repository.ReviewRepository;
import com.example.Triple_clone.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final static int PAGE_SIZE = 5;
    private final PlaceRepository placeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    public RecommendReadDto findById(long placeId, long userId) {
        Optional<Place> place = placeRepository.findById(placeId);

        Place exsitPlace = place.orElseThrow(() -> new IllegalArgumentException("no place entity"));
        boolean likeOrNot = exsitPlace.getLikes().contains(userId);

        return new RecommendReadDto(exsitPlace, likeOrNot);
    }

    public Page<RecommendReadDto> findAll(String orderType, Pageable pageable) {
        Page<Place> placesPage;
        Pageable customPageable;

        switch (orderType) {
            case "name":
                customPageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by("title").descending());
                placesPage = placeRepository.findAllByOrderByTitleDesc(customPageable);
                break;
            default:
                customPageable = PageRequest.of(pageable.getPageNumber(), PAGE_SIZE, Sort.by("date").descending());
                placesPage = placeRepository.findAllByOrderByDateDesc(customPageable);
                break;
        }

        List<RecommendReadDto> dtos = placesPage.getContent().stream()
                .map(place -> new RecommendReadDto(place, false))
                .toList();

        return new PageImpl<>(dtos, pageable, placesPage.getTotalElements());
    }

    @Transactional
    public void like(long placeId, Long userId) {
        placeRepository.saveLike(userId, placeId);
    }

    @Transactional
    public void writeReview(RecommendWriteReviewDto writeReviewRequestDto) {
        Optional<Place> place = placeRepository.findById(writeReviewRequestDto.placeId());
        Optional<User> user = userRepository.findById(writeReviewRequestDto.userId());

        Place exsitPlace = place.orElseThrow(() -> new IllegalArgumentException("no place entity"));
        User writer = user.orElseThrow(() -> new IllegalArgumentException("no user entity"));

        Review review = writeReviewRequestDto.toEntity(writer, exsitPlace);
        reviewRepository.save(review);
        exsitPlace.getReviews().add(review);
    }
}