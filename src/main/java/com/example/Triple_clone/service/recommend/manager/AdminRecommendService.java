package com.example.Triple_clone.service.recommend.manager;

import com.example.Triple_clone.dto.recommend.manager.AdminRecommendCreatePlaceDto;
import com.example.Triple_clone.dto.recommend.manager.AdminRecommendUpdatePlaceDto;
import com.example.Triple_clone.domain.entity.Place;
import com.example.Triple_clone.repository.PlaceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminRecommendService {
    private final PlaceRepository repository;

    public Place createPlace(AdminRecommendCreatePlaceDto createPlaceRequestDto) {
        Place place = createPlaceRequestDto.toEntity();
        repository.save(place);
        return place;
    }

    @Transactional
    public Place updatePlace(AdminRecommendUpdatePlaceDto updatePlaceRequestDto) {
        Optional<Place> place = repository.findById(updatePlaceRequestDto.placeId());
        Place target = place.orElseThrow(() -> new NoSuchElementException("no place entity for update"));

        target.update(updatePlaceRequestDto.title(),
                updatePlaceRequestDto.notionUrl(),
                updatePlaceRequestDto.subTitle(),
                updatePlaceRequestDto.location(),
                updatePlaceRequestDto.mainImage());

        return target;
    }

    @Transactional
    public long deletePlace(Long placeId) {
        Optional<Place> place = repository.findById(placeId);
        Place target = place.orElseThrow(() -> new NoSuchElementException("no place entity for delete"));

        repository.delete(target);
        return placeId;
    }
}
