package com.ticketsystem;

import com.ticketsystem.Configuration.DotEnvConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TicketSystemApplication {

    public static void main(String[] args) {
        DotEnvConfig dotEnvConfig = new DotEnvConfig();
        Dotenv dotenv = dotEnvConfig.dotenv();

        dotenv.entries().forEach(
                entry -> System.setProperty(entry.getKey(), entry.getValue())
        );

        SpringApplication.run(TicketSystemApplication.class, args);
    }

}
