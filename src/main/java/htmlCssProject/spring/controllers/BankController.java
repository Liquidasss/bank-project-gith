package htmlCssProject.spring.controllers;

import htmlCssProject.spring.dto.BankDto;
import htmlCssProject.spring.services.BankService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/bank")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BankController {

    public static final String ADD_USER_TO_DB = "/addAccount";
    public static final String BLOCK_ACCOUNT = "/blockUser";
    public static final String UNBLOCK_ACCOUNT = "/unblockUser";

    BankService bankService;

    @PostMapping(ADD_USER_TO_DB)
    public ResponseEntity<String> addAccount(@RequestBody BankDto bankDto){
        bankService.addAccount(bankDto.getId());
        return ResponseEntity.ok("Account has created");
    }

    @PatchMapping(BLOCK_ACCOUNT)
    public ResponseEntity<String> blockUser(@RequestBody BankDto bankDto){
        bankService.blockUserById(bankDto.getId());
        return ResponseEntity.ok("Account has blocked");
    }

    @PatchMapping(UNBLOCK_ACCOUNT)
    public ResponseEntity<String> unblockUser(@RequestBody BankDto bankDto){
        bankService.unblockUserById(bankDto.getId());
        return ResponseEntity.ok("Account has blocked");
    }



}
