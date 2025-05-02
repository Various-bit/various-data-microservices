package org.example.tracker.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Converter
public class DoubleListConverter implements AttributeConverter<List<Double>, String> {
    private static final String DELIMITER = "#";

    @Override
    public String convertToDatabaseColumn(List<Double> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<Double> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(dbData.split(DELIMITER))
                .map(String::trim)
                .filter(s -> !s.isEmpty()) // Игнорируем пустые значения
                .map(s -> {
                    try {
                        return Double.parseDouble(s);
                    } catch (NumberFormatException e) {
                        // Логируем ошибку и возвращаем null (или другое значение по умолчанию)
                        System.err.println("Invalid double value: " + s);
                        return null; // или throw исключение
                    }
                })
                .filter(Objects::nonNull) // Игнорируем null
                .collect(Collectors.toList());
    }
}