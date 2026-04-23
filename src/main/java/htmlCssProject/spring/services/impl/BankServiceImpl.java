package htmlCssProject.spring.services.impl;

import org.springframework.http.ResponseEntity;

public interface BankServiceImpl {

    public ResponseEntity<String> addAccount(Long id);

    public ResponseEntity<String> blockUserById(Long id);

    public ResponseEntity<String> unblockUserById(Long id);

}
