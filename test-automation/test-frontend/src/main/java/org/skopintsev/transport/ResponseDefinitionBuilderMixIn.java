package org.skopintsev.transport;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

// Tells Jackson to serialize all fields regardless of their visibility (private, protected, etc.)
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ResponseDefinitionBuilderMixIn {
    // The class itself is empty - it just serves as a carrier for the annotation
}
