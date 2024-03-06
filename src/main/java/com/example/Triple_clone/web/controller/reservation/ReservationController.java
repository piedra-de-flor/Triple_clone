package com.example.Triple_clone.web.controller.reservation;

import com.example.Triple_clone.dto.planning.DetailPlanDto;
import com.example.Triple_clone.service.recommend.user.ReservationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "숙소 예약 Controller", description = "RESERVATION API")
public class ReservationController {
    private final ReservationService reservationService;

    @Operation(summary = "내가 예약한 숙소 조회", description = "내가 예약한 숙소들을 조회합니다")
    @ApiResponse(responseCode = "200", description = "성공")
    @ApiResponse(responseCode = "400", description = "잘못된 요청 형식입니다")
    @ApiResponse(responseCode = "500", description = "내부 서버 오류 발생")
    @GetMapping("/myReservations")
    public ResponseEntity<List<DetailPlanDto>> findAllMyReservations(
            @Parameter(description = "내 예약 조회 요청 정보 (내 USER ID)", required = true)
            @RequestParam long userId) {
        List<DetailPlanDto> response = reservationService.findAllMyReservation(userId);
        return ResponseEntity.ok(response);
    }
}
