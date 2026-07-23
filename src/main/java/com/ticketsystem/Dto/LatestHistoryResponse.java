package com.ticketsystem.Dto;

import java.util.List;

public record LatestHistoryResponse(
        Long maxId,
        List<HistoryResponseDto> items
) {
}
