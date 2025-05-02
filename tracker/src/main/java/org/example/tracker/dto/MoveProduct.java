package org.example.tracker.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class MoveProduct {
    UUID productUuid;
    UUID categoryUuid;
}
