package com.example.Triple_clone.repository;

import com.example.Triple_clone.entity.Place;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Page<Place> findAllByOrderByTitleDesc(Pageable pageable);

    Page<Place> findAllByOrderByDateDesc(Pageable pageable);

    default void saveLike(long userId, long placeId) {
        Optional<Place> place = findById(placeId);

        if (place.isEmpty()) {
            throw new RuntimeException("no entity place");
        }

        Place exsitPlace = place.get();
        List<Long> likes = exsitPlace.getLikes();

        if (likes.contains(userId)) {
            likes.remove(userId);
        } else {
            likes.add(userId);
        }

        save(exsitPlace);
    }
}
