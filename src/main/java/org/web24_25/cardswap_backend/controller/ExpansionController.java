package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.requests.AddCard;
import org.web24_25.cardswap_backend.requests.AddExpansion;
import org.web24_25.cardswap_backend.service.ExpansionService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/expansions")
public class ExpansionController {
    private final ExpansionService expansionService = new ExpansionService();

    @GetMapping("/getExpansions")
    public ResponseEntity<String> getExpansions() {
        return expansionService.getExpansions();
    }

    @GetMapping("/getExpansion/{id}")
    public ResponseEntity<String> getExpansion(@PathVariable int id) {
        return expansionService.getExpansion(id);
    }

    @PostMapping("/AddExpansion")
    public ResponseEntity<String> addExpansion(@RequestBody AddExpansion expansion) {
        return expansionService.addExpansion(expansion);
    }

    @GetMapping("/removeExpansion/{id}")
    public ResponseEntity<String> removeExpansion(@PathVariable int id) {
        return expansionService.removeExpansion(id);
    }
}
