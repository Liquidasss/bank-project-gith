package htmlCssProject.spring.controllers;

import htmlCssProject.spring.requests.*;
import htmlCssProject.spring.services.TransactionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/transaction")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TransactionController {

    public static final String DEPOSIT_MONEY_TO_USER = "/depositMoney";
    public static final String WITHDRAW_MONEY = "/withdrawMoney";
    public static final String TRANSFER = "/transferMoney";
    public static final String INVEST_MONEY = "/investMoney";
    public static final String LOAN_MONEY = "/loanMoney";
    public static final String PAY_FOR_LOAN = "/payForLoan";

    TransactionService transactionService;

    @PostMapping(TRANSFER)
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) {
        transactionService.transaction(transferRequest.getSenderId(), transferRequest.getReceiverId(), transferRequest.getAmount());
        return ResponseEntity.ok("all is fine transfer success");
    }

    @PostMapping(DEPOSIT_MONEY_TO_USER)
    public ResponseEntity<String> depositMoney(@RequestBody DepositRequest depositRequest){
        transactionService.depositMoney(depositRequest.getSenderId(), depositRequest.getAmount());
        return ResponseEntity.ok("all is fine deposit success");
    }

    @PostMapping(WITHDRAW_MONEY)
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawRequest withdrawRequest){
        transactionService.withdrawMoney(withdrawRequest.getReceiverId(), withdrawRequest.getAmount());

        return ResponseEntity.ok("all is fine withdraw success");
    }

    @PostMapping(INVEST_MONEY)
    public ResponseEntity<String> investMoney(@RequestBody InvestRequest investRequest){
        transactionService.investment(investRequest.getSenderId(), investRequest.getAmount(), investRequest.getRate(), investRequest.getMonth());

        return ResponseEntity.ok("Invest has successfully done");
    }

    @PostMapping(LOAN_MONEY)
    public ResponseEntity<String> claimLoan(@RequestBody ClaimLoanRequest claimLoanRequest){
        transactionService.claimLoan(claimLoanRequest.getReceiverId(), claimLoanRequest.getAmount(), claimLoanRequest.getMonth());

        return ResponseEntity.ok("All is fine credit claimed");
    }

    @PostMapping(PAY_FOR_LOAN)
    public ResponseEntity<String> loanPaid(@RequestBody LoanPaidRequest loanPaidRequest){
        transactionService.loanPaid(loanPaidRequest.getReceiverId(), loanPaidRequest.getAmount());

        return ResponseEntity.ok("All is fine u have credit");
    }

}
