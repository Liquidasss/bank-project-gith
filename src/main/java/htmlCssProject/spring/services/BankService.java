package htmlCssProject.spring.services;

import htmlCssProject.spring.entities.BankEntity;
import htmlCssProject.spring.exeptions.AccountNotFoundException;
import htmlCssProject.spring.exeptions.BadRequestException;
import htmlCssProject.spring.repositories.BankRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankService {

    BankRepository bankRepository;

    public ResponseEntity<String> addAccount(Long id) {

        bankRepository
                .findById(id)
                .ifPresent(user -> {
                    log.warn("Somebody tried to create user with ID {}", id);
                    throw new BadRequestException(String.format("A user with ID \"%s\" exists.", id));
                });

        BankEntity user = bankRepository.saveAndFlush(
                BankEntity.builder()
                        .id(id)
                        .balance(BigDecimal.ZERO)
                        .isBlocked(false)
                        .build()
        );

        log.info("User successfully created with ID {}", id);

        return ResponseEntity.ok("Account has added");
    }

    public ResponseEntity<String> blockUserById(Long id){

        BankEntity blockUser = bankRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(
                                String.format("ID {} not found", id)
                        )
                );
        if(blockUser.getIsBlocked()){
            throw new BadRequestException(String.format("User ID already blocked"));
        }

        blockUser.setIsBlocked(true);
        bankRepository.saveAndFlush(blockUser);

        return ResponseEntity.ok("User has blocked");

    }

    public ResponseEntity<String> unblockUserById(Long id){

        BankEntity unblockUser = bankRepository
                .findById(id)
                .orElseThrow(() -> new AccountNotFoundException(
                                String.format(
                                        "ID {} not found", id)
                        )
                );
        if(!unblockUser.getIsBlocked()){
            throw new BadRequestException(String.format("Account already unblocked"));
        }

        unblockUser.setIsBlocked(false);
        bankRepository.saveAndFlush(unblockUser);

        return ResponseEntity.ok("User has unblocked");
    }
}
