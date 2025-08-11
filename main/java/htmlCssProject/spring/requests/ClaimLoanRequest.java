package htmlCssProject.spring.requests;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClaimLoanRequest {
    Long receiverId;
    BigDecimal amount;
    Integer month;
}
