package com.example.Triple_clone.dto.recommend.user;

import com.example.Triple_clone.entity.Place;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class RecommendForUserReadAllResponseDto {
    private final List<Place> places;
}
