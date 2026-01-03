package org.skopintsev.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.MetalType;

@Data
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Currency {
    String currencyCode;
    String currencyName;
    @Builder.Default String currencySymbol = "*";
    @Builder.Default String metalType = MetalType.UNKNOWN.getText();
}
