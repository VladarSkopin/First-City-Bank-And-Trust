package org.skopintsev.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends JsonSerializer<LocalDate> {
    @Override
    public void serialize(LocalDate date, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        // Formats date as ISO standard (YYYY-MM-DD)
        gen.writeString(date.format(DateTimeFormatter.ISO_LOCAL_DATE));
    }
}
