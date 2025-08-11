package htmlCssProject.spring.repositories;

import htmlCssProject.spring.entities.BankEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BankRepository extends JpaRepository<BankEntity, Long> {
    List<BankEntity> id(Long id);
}
