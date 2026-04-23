package htmlCssProject.spring.controllers;

import htmlCssProject.spring.requests.LoanRequest;
import htmlCssProject.spring.requests.WithdrawRequest;
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
@RequestMapping("/withdraw")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WithdrawController {

    TransactionService transactionService;

    @PostMapping("/withdraw_money")
    public ResponseEntity<String> withdrawMoney(@RequestBody WithdrawRequest withdrawRequest){
        transactionService.withdrawMoney(withdrawRequest.getId(), withdrawRequest.getAmount());

        return ResponseEntity.ok("all is fine withdraw success");
    }

}
