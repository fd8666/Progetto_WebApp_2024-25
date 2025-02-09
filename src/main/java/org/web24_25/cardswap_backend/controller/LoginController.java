package org.web24_25.cardswap_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.UsersTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.requests.GoogleLogin;
import org.web24_25.cardswap_backend.requests.PasswordLogin;
import org.web24_25.cardswap_backend.service.LoginService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/login")
public class LoginController {
    private final LoginService service = new LoginService();

    @PostMapping("/password")
    public ResponseEntity<String> loginPassword(@RequestBody PasswordLogin data, HttpSession session) {
        return service.loginPassword(data, session);
    }

    @PostMapping("/google")
    public ResponseEntity<String> loginGoogle(@RequestBody GoogleLogin data, HttpSession session) {
        return service.loginGoogle(data, session);
    }
    @GetMapping("/user/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        UserEntry user = UsersTablePostgres.getInstance().getUserFromEmail(email);

        if (user == null) {
            return ResponseEntity.status(404).body("{\"error\":\"User not found\"}");
        }

        return ResponseEntity.ok(user);
    }


}