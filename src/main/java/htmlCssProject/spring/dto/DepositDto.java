package htmlCssProject.spring.dto;

import htmlCssProject.spring.enums.TransactionTypeEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositDto {

    @NonNull
    Long id;

    @NonNull
    BigDecimal amount;

    @NonNull
    Boolean isBlocked;

    @Enumerated(EnumType.STRING)
    TransactionTypeEnum type;

}
