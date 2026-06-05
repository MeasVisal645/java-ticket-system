package com.ticketsystem.Dto;

import com.ticketsystem.Entities.Status;

public record UpdateStatusRequest(
        Status status
) {
}
