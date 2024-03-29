package com.example.Triple_clone.web.controller.recommend.user;

import com.example.Triple_clone.dto.recommend.user.RecommendLikeDto;
import com.example.Triple_clone.dto.recommend.user.RecommendReadDto;
import com.example.Triple_clone.service.recommend.user.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequiredArgsConstructor
public class RecommendController {
    private static final String REDIRECT_END_POINT_TO_PLANNING_SERVICE = "";
    private final RecommendService service;

    @GetMapping("/recommend/places")
    public ResponseEntity<Page<RecommendReadDto>> readAllOrderBy(@RequestParam(required = false, defaultValue = "") String sort,
                                                                 Pageable pageable) {
        return ResponseEntity.ok(service.findAll(sort, pageable));
    }

    @GetMapping("/recommend/place")
    public ResponseEntity<RecommendReadDto> read(@RequestParam long placeId, @RequestParam long userId) {
        RecommendReadDto recommendReadDto = service.getById(placeId, userId);
        return ResponseEntity.ok(recommendReadDto);
    }

    @PutMapping("/recommend/place/like")
    public ResponseEntity<RecommendLikeDto> like(@RequestBody RecommendLikeDto recommendLikeDto) {
        service.like(recommendLikeDto.placeId(), recommendLikeDto.userId());
        return ResponseEntity.ok(recommendLikeDto);
    }

    //TODO
    //FIXME
    //    // - 추후에 추천 장소를 내 계획에 추가하는 로직 구현 예정
    @GetMapping("/recommend/user/plan")
    public String redirectToPlanning(@RequestParam String target, @RequestParam long placeId, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("placeId", placeId);
        return "redirect:/" + REDIRECT_END_POINT_TO_PLANNING_SERVICE + target;
    }
}
