package com.example.Triple_clone.service.planning;

import com.example.Triple_clone.domain.entity.Member;
import com.example.Triple_clone.domain.entity.Plan;
import com.example.Triple_clone.domain.vo.AuthErrorCode;
import com.example.Triple_clone.dto.planning.*;
import com.example.Triple_clone.service.membership.UserService;
import com.example.Triple_clone.web.exception.RestApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanFacadeService {
    private final UserService userService;
    private final PlanService planService;

    private Member member;
    private Plan plan;

    public PlanCreateDto create(PlanCreateDto createDto) {
        member = userService.findById(createDto.userId());
        Plan plan = createDto.toEntity(member);
        planService.save(plan);
        return createDto;
    }

    private void isExist(PlanDto dto) {
        member = userService.findById(dto.userId());
        plan = planService.findById(dto.planId());
    }

    public PlanReadResponseDto findPlan(PlanDto readRequestDto) {
        isExist(readRequestDto);
        if (plan.isMine(member.getId())) {
            return new PlanReadResponseDto(plan);
        }

        throw new RestApiException(AuthErrorCode.AUTH_ERROR_CODE);
    }

    public PlanReadAllResponseDto findAllPlan(long userId) {
        Member member = userService.findById(userId);
        List<PlanReadResponseDto> plans = new ArrayList<>();

        for (Plan plan : member.getPlans()) {
            plans.add(new PlanReadResponseDto(plan));
        }
        return new PlanReadAllResponseDto(plans);
    }

    public PlanStyleUpdateDto updateStyle(PlanStyleUpdateDto updateDto) {
        isExist(updateDto.planDto());

        if (plan.isMine(member.getId())) {
            planService.updateStyle(updateDto);
            return updateDto;
        }

        throw new RestApiException(AuthErrorCode.AUTH_ERROR_CODE);
    }

    public PlanPartnerUpdateDto updatePartner(PlanPartnerUpdateDto updateDto) {
        isExist(updateDto.planDto());

        if (plan.isMine(member.getId())) {
            planService.updatePartner(updateDto);
            return updateDto;
        }

        throw new RestApiException(AuthErrorCode.AUTH_ERROR_CODE);
    }

    public PlanDto deletePlan(PlanDto deleteDto) {
        isExist(deleteDto);

        if (plan.isMine(member.getId())) {
            planService.delete(plan);
            return deleteDto;
        }

        throw new RestApiException(AuthErrorCode.AUTH_ERROR_CODE);
    }
}
