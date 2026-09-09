package com.example.flashsale.controller;

import com.example.flashsale.service.WaitingQueueService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class QueueController {

    private final WaitingQueueService queueService;

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
}