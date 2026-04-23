package htmlCssProject.spring.controllers;

import htmlCssProject.spring.requests.InvestRequest;
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
@RequestMapping("/investment")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InvestmentController {

    TransactionService transactionService;

    @PostMapping("/investMoney")
    public ResponseEntity<String> investMoney(@RequestBody InvestRequest investRequest){
        transactionService.investment(investRequest.getId(), investRequest.getAmount(),
                                      investRequest.getRate(), investRequest.getMonth());

        return ResponseEntity.ok("Invest has successfully done");
    }

}
