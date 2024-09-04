package br.com.tasklist.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringDocApiConfig {

    @Bean
    public OpenAPI DocApi(){
        return new OpenAPI()
            .info(
                new Info()
                        .title("Documentação TaskList API")
                        .description("API para controle de lista de tarefas")
                        .version("V1.0")
                        .contact(new Contact().name(": Bruno Gaspar Romeiro Barbosa - email: bgrbarbosa@hotmail.com").email("bgrbarbosa@hotmail.com"))

            );
    }
}
