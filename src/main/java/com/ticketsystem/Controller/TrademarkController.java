package com.ticketsystem.Controller;

import com.ticketsystem.Dto.TrademarkDto;
import com.ticketsystem.Service.TrademarkService;
import com.ticketsystem.Utils.ApiResponse;
import com.ticketsystem.Utils.PageResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/trademark")
public class TrademarkController {

    private final TrademarkService trademarkService;

    @GetMapping("/all")
    public Mono<ApiResponse<?>> findAll() {
        return trademarkService.findAll()
                .collectList()
                .map(ApiResponse::success);
    }

    @GetMapping("/{id}")
    public Mono<ApiResponse<?>> findById(@PathVariable Long id) {
        return trademarkService.findById(id)
                .map(ApiResponse::success);
    }

    @PostMapping("/create")
    public Mono<ApiResponse<?>> create(@RequestBody TrademarkDto trademarkDto) {
        return trademarkService.create(trademarkDto)
                .map(ApiResponse::created);
    }

    @PutMapping("/update/{id}")
    public Mono<ApiResponse<?>> update(@RequestBody TrademarkDto trademarkDto) {
        return trademarkService.update(trademarkDto)
                .map(ApiResponse::updated);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<ApiResponse<?>> delete(@PathVariable Long id) {
        return trademarkService.delete(id)
                .thenReturn(ApiResponse.deleted("Deleted Success"));
    }

    @GetMapping
    public Mono<ApiResponse<PageResponse<?>>> findPagination(
            @RequestParam(required = false) Integer pageSize,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) Boolean isActive
    ) {
        return trademarkService.findPagination(
                        pageSize,
                        pageNumber,
                        search,
                        isActive)
                .map(ApiResponse::success);
    }
}
