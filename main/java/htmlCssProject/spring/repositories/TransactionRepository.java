package htmlCssProject.spring.repositories;

import htmlCssProject.spring.entities.TransactionEntity;
import htmlCssProject.spring.enums.TransactionTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionRepository extends JpaRepository<TransactionEntity, Long> {
    Optional<TransactionEntity> findTopByReceiverIdAndTypeOrderByIdDesc(Long receiverId, TransactionTypeEnum type);
}
