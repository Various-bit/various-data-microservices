package org.example.tracker.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Converter
public class StringListConverter implements AttributeConverter<List<String>, String> {
    private static final String DELIMITER = ","; // Разделитель

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null || list.isEmpty()) {
            return ""; // Если список пуст, возвращаем пустую строку
        }
        return String.join(DELIMITER, list); // Объединяем элементы через разделитель
    }

    @Override
    public List<String> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return new ArrayList<>(); // Если строка пуста, возвращаем пустой список
        }
        return Arrays.asList(dbData.split(DELIMITER)); // Разбиваем строку по разделителю
    }
}
