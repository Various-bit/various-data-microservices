package org.example.tracker.config.error;

import lombok.Data;

@Data
public class AppError {
    private String statusCode;
    private String message;

    public AppError() {
    }

    public AppError(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
