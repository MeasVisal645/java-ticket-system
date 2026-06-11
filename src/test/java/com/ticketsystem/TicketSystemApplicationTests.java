package com.ticketsystem;

import com.ticketsystem.Configuration.DotEnvConfig;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.Test;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = TicketSystemApplication.class)
@ConfigurationPropertiesScan
class TicketSystemApplicationTests {

    @Test
    void contextLoads() {
        DotEnvConfig dotEnvConfig = new DotEnvConfig();
        Dotenv dotenv = dotEnvConfig.dotenv();

        dotenv.entries().forEach(
                entry -> System.setProperty(entry.getKey(), entry.getValue())
        );
    }

}
