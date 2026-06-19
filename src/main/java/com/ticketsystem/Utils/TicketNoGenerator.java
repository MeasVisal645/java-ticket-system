package com.ticketsystem.Utils;

import com.ticketsystem.Repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class TicketNoGenerator {

    private final TicketRepository ticketRepository;

    private static int counter = 1;

    public static synchronized String generateStaticTicketNo() {
        return "TICKET" + "-" + String.format("%04d", counter++);
    }

    public Mono<String> generateTicketNo() {
        return ticketRepository.findMaxTicketNo()
                .defaultIfEmpty("TICKET-0000")
                .map(lastTicketNo -> {
                    int lastNumber = Integer.parseInt(lastTicketNo.split("-")[1]);
                    int next = lastNumber + 1;
                    return "TICKET-" + String.format("%04d", next);
                });
    }
}
