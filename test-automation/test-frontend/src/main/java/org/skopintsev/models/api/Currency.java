package org.skopintsev.models.api;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {
    String currencyCode;
    String currencyName;
    String currencySymbol;
    String metalType;
}
