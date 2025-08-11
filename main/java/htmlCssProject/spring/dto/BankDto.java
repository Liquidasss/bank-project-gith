package htmlCssProject.spring.dto;

import htmlCssProject.spring.entities.TransactionEntity;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankDto {

    @NonNull
    Long id;

    List<TransactionEntity> sendTransaction;

    List<TransactionEntity> receiveTransaction;

    @NonNull
    BigDecimal balance;

    @NonNull
    boolean isBlocked;
}
