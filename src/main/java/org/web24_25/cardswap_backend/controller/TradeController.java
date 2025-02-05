package org.web24_25.cardswap_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.requests.CreateTrade;
import org.web24_25.cardswap_backend.requests.PasswordRegistration;
import org.web24_25.cardswap_backend.service.TradeService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/trade")
public class TradeController {
    private final TradeService service = new TradeService();

    @PostMapping("/create")
    public ResponseEntity<String> createTrade(@RequestBody CreateTrade trade, HttpSession session) {
        return service.createTrade(trade, session);
    }

    @GetMapping("/getTrade/{id}")
    public ResponseEntity<String> getTrade(@PathVariable int id, HttpSession session) {
        return service.getTrade(id, session);
    }

    @GetMapping("/getOffers/{id}")
    public ResponseEntity<String> getOffers(@PathVariable int id, HttpSession session) {
        return service.getOffers(id, session);
    }

    @GetMapping("/getRequests/{id}")
    public ResponseEntity<String> getRequests(@PathVariable int id, HttpSession session) {
        return service.getRequests(id, session);
    }

    @GetMapping("/getTradesToUser")
    public ResponseEntity<String> getTradesToUser(HttpSession session) {
        return service.getTradesToUser(session);
    }

    @GetMapping("/getTradesFromUser")
    public ResponseEntity<String> getTradesFromUser(HttpSession session) {
        return service.getTradesFromUser(session);
    }

    @GetMapping("/getTradesToUserRange/{start}/{limit}")
    public ResponseEntity<String> getTradesToUserRange(@PathVariable int start, @PathVariable int limit, HttpSession session) {
        return service.getTradesToUserRange(start, limit, session);
    }

    @GetMapping("/getTradesFromUserRange/{start}/{limit}")
    public ResponseEntity<String> getTradesFromUserRange(@PathVariable int start, @PathVariable int limit, HttpSession session) {
        return service.getTradesFromUserRange(start, limit, session);
    }

    @GetMapping("/accept/{id}")
    public ResponseEntity<String> acceptTrade(@PathVariable int id, HttpSession session) {
        return service.acceptTrade(id, session);
    }

    @GetMapping("/reject/{id}")
    public ResponseEntity<String> rejectTrade(@PathVariable int id, HttpSession session) {
        return service.rejectTrade(id, session);
    }
}
