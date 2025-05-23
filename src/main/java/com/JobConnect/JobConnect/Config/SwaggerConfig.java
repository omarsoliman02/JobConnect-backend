package com.JobConnect.JobConnect.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "JobConnect API", 
        version = "1.0.0", 
        description = "Job portal API for managing companies, jobs, applicants and applications"
    )
)
public class SwaggerConfig {
    
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
            .info(new io.swagger.v3.oas.models.info.Info()
                .title("JobConnect API")
                .version("1.0.0")
                .description("Job portal API for managing companies, jobs, applicants and applications")
                .license(new License()
                    .name("MIT License")
                    .url("https://opensource.org/licenses/MIT")));
    }
} 