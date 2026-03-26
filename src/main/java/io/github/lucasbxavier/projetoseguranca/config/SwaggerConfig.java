package io.github.lucasbxavier.projetoseguranca.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.java.Log;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Log
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI().info(new Info()
                .title("API de Login projeto de segurança da informação")
                .version("v1")
                .description("Documentação da API de Login projeto de segurança da informação"));
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> swaggerStartupLogger(
            Environment environment) {

        return event -> {
            String port =
                    environment.getProperty("server.port", "8080");

            String contextPath =
                    environment.getProperty(
                            "server.servlet.context-path", "");

            String swaggerPath =
                    environment.getProperty(
                            "springdoc.swagger-ui.path",
                            "/swagger-ui.html");

            String swaggerUrl =
                    "http://localhost:" + port
                            + contextPath
                            + swaggerPath;

            log.info("🚀 Swagger UI disponível em:");
            log.info("👉 " + swaggerUrl);
        };
    }
}
