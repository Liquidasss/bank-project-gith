package htmlCssProject.spring.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestRequest {
    Long Id;
    BigDecimal amount;
    Double rate;
    Integer month;
}
