package org.skopintsev.models.api.client;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClientType {
    String clientTypeCode;
    String clientTypeName;
    String description;
}
