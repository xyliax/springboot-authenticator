package com.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@Configuration
@ConfigurationProperties(prefix = "file.mapping")
public class FileMappingConfig implements WebMvcConfigurer {
    public static String fileRealPath = "/Users/peiyuxing/content-file/test/";
    public static String fileMappingPath = "/content-file/**";
    public static String fileMappingPathPrefix = "/content-file/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(fileMappingPath)
                .addResourceLocations("file:" + fileRealPath);
    }
}

