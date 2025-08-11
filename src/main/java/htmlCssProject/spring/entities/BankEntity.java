package htmlCssProject.spring.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "bank")
public class BankEntity {

    @Id
    @Column(name = "id", unique = true, length = 8)
    Long id;

    @Column(name = "balance")
    BigDecimal balance;

    @OneToMany(mappedBy = "sender")
    @JsonIgnore
    List<TransactionEntity> senderId;

    @OneToMany(mappedBy = "receiver")
    @JsonIgnore
    List<TransactionEntity> receiverId;

    @Column(name = "is_blocked")
    Boolean isBlocked;

}
