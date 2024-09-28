package com.example.Triple_clone.service.planning;

import com.example.Triple_clone.domain.entity.Accommodation;
import com.example.Triple_clone.domain.entity.DetailPlan;
import com.example.Triple_clone.domain.entity.Plan;
import com.example.Triple_clone.domain.entity.Recommendation;
import com.example.Triple_clone.dto.planning.DetailPlanDto;
import com.example.Triple_clone.dto.planning.DetailPlanUpdateDto;
import com.example.Triple_clone.dto.planning.ReservationCreateDto;
import com.example.Triple_clone.service.accommodation.AccommodationService;
import com.example.Triple_clone.service.recommend.user.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@Service
public class DetailPlanFacadeService {
    private final PlanService planService;
    private final DetailPlanService detailPlanService;
    private final RecommendService recommendService;
    private final AccommodationService accommodationService;

    public DetailPlanDto create(DetailPlanDto detailPlanDto) {
        Plan plan = planService.findById(detailPlanDto.planId());
        DetailPlan detailPlan = detailPlanDto.toEntity(plan);

        detailPlanService.save(detailPlan);

        return detailPlanDto;
    }

    public DetailPlanDto addRecommendation(long recommendationId, long planId) {
        Plan plan = planService.findById(planId);
        Recommendation recommendation = recommendService.findById(recommendationId);

        DetailPlanDto detailPlanDto = new DetailPlanDto(planId, recommendation.getLocation(), plan.getStartDay(), null);
        this.create(detailPlanDto);

        return detailPlanDto;
    }

    public ReservationCreateDto createReservation(ReservationCreateDto reservationCreateDto) {
        Plan plan = planService.findById(reservationCreateDto.planId());
        Accommodation accommodation = accommodationService.findById(reservationCreateDto.accommodationId());
        DetailPlan detailPlan = reservationCreateDto.toEntity(plan, accommodation);

        detailPlanService.save(detailPlan);

        return reservationCreateDto;
    }

    public List<DetailPlanDto> readAll(long planId) {
        Plan plan = planService.findById(planId);
        List<DetailPlanDto> response = new ArrayList<>();

        for (DetailPlan detailPlan : plan.getPlans()) {
            response.add(detailPlan.toDto());
        }
        return response;
    }

    public DetailPlanDto update(DetailPlanUpdateDto updateDto) {
        Plan plan = planService.findById(updateDto.planId());
        DetailPlan detailPlan = detailPlanService.findById(updateDto.detailPlanId());

        if (isContain(plan, detailPlan)) {
            detailPlanService.update(detailPlan, updateDto);

            return new DetailPlanDto(updateDto.planId(),
                    updateDto.location(),
                    updateDto.date(),
                    updateDto.time());
        }

        throw new NoSuchElementException("this detail plan id is not yours");
    }

    public DetailPlan delete(long planId, long detailPlanId) {
        Plan plan = planService.findById(planId);
        DetailPlan detailPlan = detailPlanService.findById(detailPlanId);

        if (isContain(plan, detailPlan)) {
            detailPlanService.delete(detailPlan);
            return detailPlan;
        }

        throw new NoSuchElementException("this detail plan id is not yours");
    }

    private boolean isContain(Plan plan, DetailPlan detailPlan) {
        return planService.getPlans(plan.getId()).contains(detailPlan);
    }
}
