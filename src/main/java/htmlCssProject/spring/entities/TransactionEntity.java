package htmlCssProject.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htmlCssProject.spring.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "transactions")
public class TransactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "sender")
    @JsonIgnore
    BankEntity sender;

    @ManyToOne
    @JoinColumn(name = "receiver")
    @JsonIgnore
    BankEntity receiver;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "loan")
    BigDecimal loan;

    @Column(name = "future_invest")
    BigDecimal invest;

    @Column(name = "month")
    Integer month;

    @Column(name = "rate")
    Double rate;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    TransactionTypeEnum type;

    @Column(name = "loan_paid")
    Boolean isLoanPaid;

    @Column(name = "is_blocked")
    Boolean isBlocked;

}
