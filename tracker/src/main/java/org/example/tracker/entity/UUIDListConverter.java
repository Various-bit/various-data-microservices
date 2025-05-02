package org.example.tracker.entity;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.*;
import java.util.stream.Collectors;

@Converter
public class UUIDListConverter implements AttributeConverter<List<UUID>, String> {
    private static final String DELIMITER = ",";

    @Override
    public String convertToDatabaseColumn(List<UUID> uuids) {
        return uuids != null
                ? uuids.stream().map(UUID::toString).collect(Collectors.joining(DELIMITER))
                : "";
    }

    @Override
    public List<UUID> convertToEntityAttribute(String dbData) {
        return dbData != null && !dbData.isEmpty()
                ? Arrays.stream(dbData.split(DELIMITER))
                .map(UUID::fromString)
                .collect(Collectors.toList())
                : new ArrayList<>();
    }
}


