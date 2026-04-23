package htmlCssProject.spring.controllers;

import htmlCssProject.spring.requests.LoanRequest;
import htmlCssProject.spring.services.TransactionService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/loan")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanController {

    TransactionService transactionService;

    @PostMapping("/claim_loan")
    public ResponseEntity<String> claimLoan(@RequestBody LoanRequest loanRequest){
        transactionService.claimLoan(loanRequest.getId(), loanRequest.getAmount(), loanRequest.getMonth());

        return ResponseEntity.ok("All is fine credit claimed");
    }

    @PostMapping("/paid_loan")
    public ResponseEntity<String> loanPaid(@RequestBody LoanRequest loanRequest){
        transactionService.loanPaid(loanRequest.getId(), loanRequest.getAmount());

        return ResponseEntity.ok("All is fine u have done your credit");
    }

}
