package com.ticketsystem.Utils;

import org.springframework.stereotype.Service;

@Service
public class TicketNoGenerator {
    private static int counter = 1;

    public static synchronized String generateTicketNo() {
        return "TICKET" + "-" + String.format("%04d", counter++);
    }
}
