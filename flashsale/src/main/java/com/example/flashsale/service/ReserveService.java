package com.example.flashsale.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReserveService {

    private final WaitingQueueService queueService;

    private final StockHoldService stockHoldService;

    public boolean reserve(
            Long eventId,
            Long userId
    ) {

        if (
                !queueService.isAdmitted(
                        eventId,
                        userId
                )
        ) {
            return false;
        }

        return stockHoldService.hold(
                eventId,
                userId
        );
    }
}