package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.dataStructures.GoogleRegistration;
import org.web24_25.cardswap_backend.dataStructures.PasswordRegistration;
import org.web24_25.cardswap_backend.service.LoginService;
import org.web24_25.cardswap_backend.service.RegisterService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/register")
public class RegisterController {
    private final RegisterService service = new RegisterService();
    @PostMapping("/password")
    public ResponseEntity<String> loginPassword(@RequestBody PasswordRegistration data) {
        return service.registerPassword(data);
    }

    @PostMapping("/google")
    public ResponseEntity<String> loginGoogle(@RequestBody GoogleRegistration data) {
        return service.registerGoogle(data);
    }
}
