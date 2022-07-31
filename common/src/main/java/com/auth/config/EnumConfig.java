package com.auth.config;

import com.auth.defenum.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class EnumConfig implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToRoleConverter());
        registry.addConverter(new RoleToStringConverter());
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converterList = new ArrayList<>();
        converterList.add(new RoleReadingConverter());
        converterList.add(new RoleWritingConverter());
        return new MongoCustomConversions(converterList);
    }
}

class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String roleStr) {
        try {
            return Role.valueOf(roleStr);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Role.UNKNOWN;
        }
    }
}

class RoleToStringConverter implements Converter<Role, String> {
    @Override
    public String convert(Role role) {
        if (role == Role.UNKNOWN)
            return "UNKNOWN#UNDEFINED";
        else return role.toString();
    }
}

@ReadingConverter
class RoleReadingConverter implements Converter<String, Role> {
    @Override
    public Role convert(String roleStr) {
        try {
            return Role.valueOf(roleStr);
        } catch (IllegalArgumentException illegalArgumentException) {
            return Role.UNKNOWN;
        }
    }
}

@WritingConverter
class RoleWritingConverter implements Converter<Role, String> {
    @Override
    public String convert(Role role) {
        if (role == Role.UNKNOWN)
            return "UNKNOWN#UNDEFINED";
        else return role.toString();
    }
}
