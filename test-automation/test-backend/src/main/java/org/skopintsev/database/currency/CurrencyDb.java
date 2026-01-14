package org.skopintsev.database.currency;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.skopintsev.helper.enums.MetalType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@ToString
public class CurrencyDb {
    String currencyCode;
    String currencyName;
    @Builder.Default String currencySymbol = "*";
    @Builder.Default String metalType = MetalType.UNKNOWN.getText();
}
