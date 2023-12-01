package com.example.Triple_clone.recommendTest.user;

import com.example.Triple_clone.dto.recommend.user.RecommendForUserReadResponseDto;
import com.example.Triple_clone.entity.Place;
import com.example.Triple_clone.repository.PlaceRepository;
import com.example.Triple_clone.repository.ReviewRepository;
import com.example.Triple_clone.repository.UserRepository;
import com.example.Triple_clone.service.recommend.user.RecommendForUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class RecommendForUserReadTest {
    @Autowired
    PlaceRepository repository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReviewRepository reviewrepository;
    RecommendForUserService service;
    @Mock
    Pageable pageable;

    @BeforeEach
    void setUp() {
        service = new RecommendForUserService(repository, userRepository, reviewrepository);
    }

    @Test
    void 장소_날짜순_전체_조회_테스트() throws InterruptedException {
        Place place1 = new Place("test0", "test0", "test0", "test0", "test0");
        Thread.sleep(1000);
        Place place2 = new Place("test1", "test1", "test1", "test1", "test1");
        Thread.sleep(1000);
        Place place3 = new Place("test2", "test2", "test2", "test2", "test2");

        repository.save(place3);
        repository.save(place2);
        repository.save(place1);

        Page<RecommendForUserReadResponseDto> responsePage = service.findAll("date", pageable);

        assertThat(responsePage.getContent().get(2).title()).isEqualTo(place1.getTitle());
        assertThat(responsePage.getContent().get(1).title()).isEqualTo(place2.getTitle());
        assertThat(responsePage.getContent().get(0).title()).isEqualTo(place3.getTitle());
    }

    @Test
    void 장소_이름순_전체_조회_테스트() throws InterruptedException {
        Place place1 = new Place("test0", "test0", "test0", "test0", "test0");
        Thread.sleep(1000);
        Place place2 = new Place("test1", "test1", "test1", "test1", "test1");
        Thread.sleep(1000);
        Place place3 = new Place("test2", "test2", "test2", "test2", "test2");

        repository.save(place3);
        repository.save(place2);
        repository.save(place1);

        Page<RecommendForUserReadResponseDto> responsePage = service.findAll("name", pageable);

        assertThat(responsePage.getContent().get(2).title()).isEqualTo(place1.getTitle());
        assertThat(responsePage.getContent().get(1).title()).isEqualTo(place2.getTitle());
        assertThat(responsePage.getContent().get(0).title()).isEqualTo(place3.getTitle());
    }

    @Test
    void 단일_장소_조회_테스트() {
        Place place1 = new Place("test0", "test0", "test0", "test0", "test0");
        repository.save(place1);

        RecommendForUserReadResponseDto dto = service.findById(1, 0);
        assertThat(dto.title()).isEqualTo(place1.getTitle());
        assertThat(dto.id()).isEqualTo(place1.getId());
    }
}
