package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.MetalType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Currency {
    String currencyCode;
    String currencyName;
    @Builder.Default String currencySymbol = "*";
    @Builder.Default String metalType = MetalType.UNKNOWN.getText();
}
