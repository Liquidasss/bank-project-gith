package htmlCssProject.spring.dto;

import htmlCssProject.spring.entities.BankEntity;
import htmlCssProject.spring.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionDto {

    @NonNull
    Long id;

    BankEntity sendTransaction;

    BankEntity receiveTransaction;

    @NonNull
    BigDecimal amount;

    @NonNull
    Boolean isBlocked;

    @Enumerated(EnumType.STRING)
    TransactionTypeEnum type;

}
