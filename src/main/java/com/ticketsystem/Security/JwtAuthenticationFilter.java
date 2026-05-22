package com.ticketsystem.Security;

import com.ticketsystem.Utils.JwtUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements WebFilter {

    private final JwtUtils jwtUtils;

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

       String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
       if (authHeader == null || !authHeader.startsWith("Bearer ")) {
           return chain.filter(exchange);
       }

       String token = authHeader.substring(7).trim();
        if (!jwtUtils.validateToken(token)) {
            return chain.filter(exchange);
        }

        if (jwtUtils.isRefreshToken(token)) {
            return chain.filter(exchange);
        }

       String username = jwtUtils.extractUsername(token);
       String role = jwtUtils.extractRole(token);

       var authorities = List.of(new SimpleGrantedAuthority(role));
        Authentication auth  = new UsernamePasswordAuthenticationToken(username, token, authorities);

        return chain.filter(exchange)
                .contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth));
    }
}
