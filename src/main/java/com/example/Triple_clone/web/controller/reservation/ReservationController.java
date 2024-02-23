package com.example.Triple_clone.web.controller.reservation;

import com.example.Triple_clone.service.reservation.ScrapingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReservationController {
    private final ScrapingService scrapingService;

    @GetMapping("/reservations")
    public ResponseEntity<List<String>> readAllOrderBy(@RequestParam String local,
                                                       @RequestParam(required = false, defaultValue = "") String sort,
                                                       @RequestParam long page) {
        return ResponseEntity.ok(scrapingService.findAccommodations(local, sort, page));
    }
}
