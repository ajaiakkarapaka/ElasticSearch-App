package com.example.demo.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchCustomConversions;
import org.springframework.lang.NonNull;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@Configuration
public class CustomConverters {

    @Bean
    public ElasticsearchCustomConversions elasticsearchCustomConversions() {
        return new ElasticsearchCustomConversions(Arrays.asList(new LocalDateTimeToLongConverter(), new LongToLocalDateTimeConverter()));
    }

    @ReadingConverter
    public static class LongToLocalDateTimeConverter implements Converter<Long, LocalDateTime> {
        @Override
        public LocalDateTime convert(@NonNull Long source) {
            return Instant.ofEpochMilli(source).atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
    }

    @WritingConverter
    public static class LocalDateTimeToLongConverter implements Converter<LocalDateTime, Long> {
        @Override
        public Long convert(@NonNull LocalDateTime source) {
            return source.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
    }
}
