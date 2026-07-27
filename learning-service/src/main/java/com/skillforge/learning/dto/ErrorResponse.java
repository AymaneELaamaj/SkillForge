package com.skillforge.learning.dto;

import java.time.LocalDateTime;
import java.util.Map;


public record ErrorResponse(

        LocalDateTime timestamp,

        int status,

        String code,

        String message,

        Map<String, String> errors,

        String path

) {}