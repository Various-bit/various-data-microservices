package org.example.authorization.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWT {
    private String accessToken;
    private String refreshToken;
}
