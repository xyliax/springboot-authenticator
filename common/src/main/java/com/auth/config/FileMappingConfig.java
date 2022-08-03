package com.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Data
@Configuration
@ConfigurationProperties(prefix = "spring.auth.file")
public class FileMappingConfig implements WebMvcConfigurer {
    public static String FILE_REAL_PATH = "/Users/peiyuxing/content-file/";
    public static String FILE_MAPPING_PATH = "/content-file/**";
    public static String FILE_MAPPING_PATH_PREFIX = "/content-file/";

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(FILE_MAPPING_PATH)
                .addResourceLocations("file:" + FILE_REAL_PATH);
    }
}
