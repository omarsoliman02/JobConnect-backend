package com.JobConnect.JobConnect.Controller;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenApiController {

    @GetMapping(value = "/custom-openapi", produces = MediaType.TEXT_PLAIN_VALUE)
    public String getOpenApiDocument() throws IOException {
        Resource resource = new ClassPathResource("static/openapi.yaml");
        byte[] content = Files.readAllBytes(resource.getFile().toPath());
        return new String(content, StandardCharsets.UTF_8);
    }
} 