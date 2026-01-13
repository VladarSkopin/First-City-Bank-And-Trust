package org.skopintsev.constants;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Constants {

    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            // adds support for Java 8+ Date/Time API - LocalDate, LocalDateTime, ZonedDateTime, Instant, etc.
            .registerModule(new JavaTimeModule())
            // when JSON has fields that don't exist in the Java class, Jackson will ignore them instead of throwing an exception
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            // fields with null values are excluded from JSON
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    public static final String BANK_API_URL = "http://localhost:8080";

    public static final String HTTP_HEADER_CHARSET = "Charset";

    public static final String UTF_8 = "UTF-8";

    public static final String BEARER = "Bearer ";


    // statuses

    public static final int SC_OK = 200;
}
