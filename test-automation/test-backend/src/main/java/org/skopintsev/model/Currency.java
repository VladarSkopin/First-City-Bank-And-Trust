package org.skopintsev.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.MetalTypeEnum;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Currency {
    String currencyCode;
    String currencyName;
    @Builder.Default String currencySymbol = "*";
    @Builder.Default String metalType = MetalTypeEnum.UNKNOWN.getText();
}
