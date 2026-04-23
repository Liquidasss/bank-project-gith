package htmlCssProject.spring.controllers;

import htmlCssProject.spring.requests.DepositRequest;
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
@RequestMapping("/deposit")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DepositController {

    TransactionService transactionService;

    @PostMapping("/depositMoney")
    public ResponseEntity<String> depositMoney(@RequestBody DepositRequest depositRequest){
        transactionService.depositMoney(depositRequest.getId(), depositRequest.getAmount());
        return ResponseEntity.ok("all is fine deposit success");
    }

}
