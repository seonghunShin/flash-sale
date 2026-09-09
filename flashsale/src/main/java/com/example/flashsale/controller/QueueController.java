package com.example.flashsale.controller;

import com.example.flashsale.service.ReserveService;
import com.example.flashsale.service.StockHoldService;
import com.example.flashsale.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class QueueController {

    private final WaitingQueueService queueService;
    private final ReserveService reserveService;
    private final StockHoldService stockHoldService;

    @PostMapping("/{eventId}/queue")
    public void enter(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {

        queueService.enter(
                eventId,
                userId
        );
    }

    @GetMapping("/{eventId}/queue/rank")
    public Long rank(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {

        return queueService.rank(
                eventId,
                userId
        );
    }

    @PostMapping("/{eventId}/admit")
    public Set<String> admit(
            @PathVariable Long eventId,
            @RequestParam(defaultValue = "100")
            long count
    ) {

        return queueService.admit(
                eventId,
                count
        );
    }

    @PostMapping("/{eventId}/reserve")
    public boolean reserve(
            @PathVariable Long eventId,
            @RequestParam Long userId
    ) {

        return reserveService.reserve(
                eventId,
                userId
        );
    }

    @PostMapping("/{eventId}/stock/init")
    public void initStock(
            @PathVariable Long eventId,
            @RequestParam Integer stock
    ) {

        stockHoldService.initStock(
                eventId,
                stock
        );
    }
}