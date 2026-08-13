package com.ticketsystem.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

@Configuration
public class OpenApiConfig {

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                        .components(new Components()
                                .addSecuritySchemes("bearerAuth",
                                        new SecurityScheme()
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                        );
        }

        @Bean
        public GroupedOpenApi publicApi() {
                return GroupedOpenApi.builder()
                        .group("public")
                        .pathsToMatch("/api/v1/ticket/**", "/api/v1/category/**")
                        .addOperationCustomizer(renameOperations())
                        .build();
        }

        @Bean
        public GroupedOpenApi adminApi() {
                return GroupedOpenApi.builder()
                        .group("admin")
                        .pathsToMatch("/**")
                        .addOperationCustomizer(renameOperations())
                        .build();
        }

        @Bean
        public OperationCustomizer renameOperations() {
                return (Operation operation, HandlerMethod handlerMethod) -> {
                        String methodName = handlerMethod.getMethod().getName();
                        switch (methodName) {
                                case "getAllTickets" -> operation.setSummary("List Tickets");
                                case "createTicket" -> operation.setSummary("Create Ticket");
                                case "getTicketById" -> operation.setSummary("Get Ticket Details");
                                case "updateTicket" -> operation.setSummary("Update Ticket");
                                case "deleteTicket" -> operation.setSummary("Delete Ticket");
                                default -> { }
                        }
                        return operation;
                };
        }
}