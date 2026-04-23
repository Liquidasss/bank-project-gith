package htmlCssProject.spring.services.impl;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

public interface TransactionServiceImpl {

    @Transactional
    public void transaction(Long senderId, Long receiverId, BigDecimal amount);

    @Transactional
    public void depositMoney(Long senderId, BigDecimal amount);

    @Transactional
    public void withdrawMoney(Long receiverId, BigDecimal amount);

    @Transactional
    public void investment(Long senderId, BigDecimal amount, Double rate, int month);

    @Transactional
    public void claimLoan (Long receiverId, BigDecimal amount, int month);

    @Transactional
    public void loanPaid(Long receiverId, BigDecimal amount);

}
