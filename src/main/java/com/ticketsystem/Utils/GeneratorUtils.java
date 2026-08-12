package com.ticketsystem.Utils;

import com.ticketsystem.Repository.RepairRepository;
import com.ticketsystem.Repository.TicketRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class GeneratorUtils {

    private final TicketRepository ticketRepository;
    private final RepairRepository repairRepository;

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

    public Mono<String> generateRepairNo() {
        return repairRepository.findMaxRepairNo()
                .defaultIfEmpty("REPAIR-0000")
                .map(lastNo -> {
                    int lastNumber = Integer.parseInt(lastNo.split("-")[1]);
                    int next = lastNumber + 1;
                    return "REPAIR-" + String.format("%04d", next);
                });
    }
}
