package htmlCssProject.spring.services;

import htmlCssProject.spring.entities.BankEntity;
import htmlCssProject.spring.entities.TransactionEntity;
import htmlCssProject.spring.enums.TransactionTypeEnum;
import htmlCssProject.spring.exeptions.AccountBlockedException;
import htmlCssProject.spring.exeptions.AccountNotFoundException;
import htmlCssProject.spring.exeptions.BadRequestException;
import htmlCssProject.spring.repositories.BankRepository;
import htmlCssProject.spring.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TransactionService {

    TransactionRepository transactionRepository;

    BankRepository bankRepository;

    @Transactional
    public void transaction(Long senderId, Long receiverId, BigDecimal amount){

        BankEntity sender = bankRepository
                .findById(senderId)
                .filter(enoughMoney -> enoughMoney.getBalance().compareTo(amount) >= 0)
                .filter(amountLessZero -> amount.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new AccountNotFoundException("No account found. Sender"));

        BankEntity receiver = bankRepository
                .findById(receiverId)
                .orElseThrow(() -> new AccountNotFoundException("No account found. Receiver"));

        if(sender.getIsBlocked() || receiver.getIsBlocked()){
            TransactionEntity blocked = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );

            log.info("One of the account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        sender.setBalance(sender.getBalance().subtract(amount));
        receiver.setBalance(receiver.getBalance().add(amount));

        TransactionEntity transaction = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(sender)
                        .receiver(receiver)
                        .isBlocked(false)
                        .amount(amount)
                        .type(TransactionTypeEnum.TRANSFER)
                        .build()
                );
        log.info("transaction successfully done");

    }

    @Transactional
    public void depositMoney(Long senderId, BigDecimal amount) {
        BankEntity sender = bankRepository
                .findById(senderId)
                .filter(a -> amount.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new AccountNotFoundException("No account found"));

        if (sender.getIsBlocked()) {
            transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );
            log.info("Account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        sender.setBalance(sender.getBalance().add(amount));

        transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(sender)
                        .receiver(sender)
                        .amount(amount)
                        .type(TransactionTypeEnum.DEPOSIT)
                        .build()
        );

        log.info("Balance ID {} has increased {}", senderId, amount);
    }

    @Transactional
    public void withdrawMoney(Long receiverId, BigDecimal amount) {

        BankEntity receiver = bankRepository
                .findById(receiverId)
                .filter(enoughMoney -> enoughMoney.getBalance().compareTo(amount) >= 0)
                .filter(amountLessZero -> amount.compareTo(BigDecimal.ZERO) > 0)
                .map(user -> {

                    user.setBalance(user.getBalance().subtract(amount));
                    return bankRepository.saveAndFlush(user);

                })

                .orElseThrow(()-> new BadRequestException("Account not found, or amount less zero"));

        if(receiver.getIsBlocked()){
            TransactionEntity blocked = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );
            log.info("Account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        TransactionEntity withdraw = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(receiver)
                        .receiver(receiver)
                        .isBlocked(false)
                        .amount(amount)
                        .type(TransactionTypeEnum.WITHDRAW)
                        .build()
        );
    }

    @Transactional
    public void investment(Long senderId, BigDecimal amount, Double rate, int month){

        BankEntity sender = bankRepository
                .findById(senderId)
                .filter(notEnoughMoney -> notEnoughMoney.getBalance().compareTo(amount) >= 0)
                .filter(amountLessZero -> amount.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new AccountNotFoundException("Account not found, or blocked"));

        if(sender.getIsBlocked()){
            TransactionEntity blocked = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );
            log.info("Account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        BigDecimal investCalculator = BigDecimal.valueOf(
                amount.doubleValue() * Math.pow(1 + rate / 12, 12 * (month / 12.0))
        ).setScale(2, RoundingMode.HALF_UP);

        sender.setBalance(investCalculator);

        TransactionEntity investment = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(sender)
                        .receiver(sender)
                        .amount(amount)
                        .rate(rate)
                        .month(month)
                        .isBlocked(false)
                        .invest(investCalculator)
                        .type(TransactionTypeEnum.INVESTMENTS)
                        .build()
        );

        log.info("invest success");
    }

    @Transactional
    public void claimLoan (Long receiverId, BigDecimal amount, int month){

        BankEntity receiver = bankRepository
                .findById(receiverId)
                .filter(notEnoughMoney -> notEnoughMoney.getBalance().compareTo(amount) >= 0)
                .filter(amountLessZero -> amount.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new AccountNotFoundException("Account not found"));

        if(receiver.getIsBlocked()){
            TransactionEntity blocked = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );
            log.info("Account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        Double rate = 0.12;

        BigDecimal loanCalculator = BigDecimal.valueOf(
                amount.doubleValue() * Math.pow(1 + rate / 12, month)
        ).setScale(2, RoundingMode.HALF_UP);

        TransactionEntity loan = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(receiver)
                        .receiver(receiver)
                        .isBlocked(false)
                        .amount(amount)
                        .loan(loanCalculator)
                        .month(month)
                        .rate(rate)
                        .isLoanPaid(false)
                        .type(TransactionTypeEnum.LOAN)
                        .build()
        );
        log.info("Loan success");
    }


    @Transactional
    public void loanPaid(Long receiverId, BigDecimal amount) {

        BankEntity receiver = bankRepository
                .findById(receiverId)
                .filter(account -> account.getBalance().compareTo(amount) >= 0)
                .filter(amountLessZero -> amount.compareTo(BigDecimal.ZERO) > 0)
                .orElseThrow(() -> new BadRequestException("Account not found or blocked"));

        if(receiver.getIsBlocked()){
            TransactionEntity blocked = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isBlocked(true)
                            .build()
            );
            log.info("Account blocked");
            throw new AccountBlockedException("Account blocked");
        }

        TransactionEntity loanTransaction = transactionRepository
                .findTopByReceiverIdAndTypeOrderByIdDesc(receiverId, TransactionTypeEnum.LOAN)
                .filter(loan -> loan.getLoan().compareTo(BigDecimal.ZERO) > 0)
                .filter(entity -> amount.compareTo(entity.getLoan()) <= 0)
                .orElseThrow(() -> new BadRequestException("No active loan or amount more than loan"));

        if(loanTransaction.getLoan().compareTo(amount) < 0) {
            TransactionEntity save = transactionRepository.saveAndFlush(
                    TransactionEntity.builder()
                            .isLoanPaid(true)
                            .build()
            );
            log.info("Account ID {} paid for his loan", receiverId);
        }

        BigDecimal remainingLoan = loanTransaction.getLoan().subtract(amount);
        receiver.setBalance(receiver.getBalance().subtract(amount));

        TransactionEntity loanPayment = transactionRepository.saveAndFlush(
                TransactionEntity.builder()
                        .sender(receiver)
                        .receiver(receiver)
                        .amount(amount)
                        .loan(remainingLoan)
                        .isLoanPaid(false)
                        .isBlocked(false)
                        .type(TransactionTypeEnum.PAY_FOR_LOAN)
                        .build()
        );
    }

}


