package org.example.tracker.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class SqlDateListConverter implements AttributeConverter<List<Date>, String> {
    private static final String DELIMITER = ";"; // Разделитель

    @Override
    public String convertToDatabaseColumn(List<Date> dates) {
        if (dates == null || dates.isEmpty()) {
            return null; // или пустую строку ""
        }
        return dates.stream()
                .map(Date::toString) // Преобразуем дату в строку формата "YYYY-MM-DD"
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Date> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return Arrays.stream(dbData.split(DELIMITER))
                    .map(String::trim)
                    .map(Date::valueOf) // Парсим строку в java.sql.Date
                    .collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid date format in database. Expected 'YYYY-MM-DD'", e);
        }
    }
}
