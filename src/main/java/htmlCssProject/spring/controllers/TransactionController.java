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

    TransactionService transactionService;

    @PostMapping("/transferMoney")
    public ResponseEntity<String> transfer(@RequestBody TransferRequest transferRequest) {
        transactionService.transaction(transferRequest.getSenderId(), transferRequest.getReceiverId(), transferRequest.getAmount());
        return ResponseEntity.ok("all is fine transfer success");
    }

}
