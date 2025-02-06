package org.web24_25.cardswap_backend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.service.InventoryService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/inventory")
public class InventoryController {
    private final InventoryService inventoryService = new InventoryService();

    @GetMapping("/getAllCards")
    public ResponseEntity<String> getCards(HttpSession session) {
        return inventoryService.getCards(session);
    }

//    @GetMapping("/getCardsRange/{start}/{limit}")
//    public ResponseEntity<String> getCardsRange(@PathVariable int start, @PathVariable int limit, HttpSession session) {
//        return inventoryService.getCardsRange(start, limit, session);
//    }
//
    @GetMapping("/decrement/{cardId}")
    public ResponseEntity<String> decrementCard(@PathVariable int cardId, HttpSession session) {
        return inventoryService.decrementCard(cardId, session);
    }

    @GetMapping("/increment/{cardId}")
    public ResponseEntity<String> incrementCard(@PathVariable int cardId, HttpSession session) {
        return inventoryService.incrementCard(cardId, session);
    }

    @GetMapping("/decrementAmount/{cardId}/{amount}")
    public ResponseEntity<String> decrementCardAmount(@PathVariable int cardId, @PathVariable int amount, HttpSession session) {
        return inventoryService.decrementCardAmount(cardId, amount, session);
    }

    @GetMapping("/incrementAmount/{cardId}/{amount}")
    public ResponseEntity<String> incrementCardAmount(@PathVariable int cardId, @PathVariable int amount, HttpSession session) {
        return inventoryService.incrementCardAmount(cardId, amount, session);
    }

    @GetMapping("/add/{cardId}/{amount}")
    public ResponseEntity<String> addCard(@PathVariable int cardId, @PathVariable int amount, HttpSession session) {
        return inventoryService.addCard(cardId, amount, session);
    }

    @GetMapping("/remove/{cardId}")
    public ResponseEntity<String> removeCard(@PathVariable int cardId, HttpSession session) {
        return inventoryService.removeCard(cardId, session);
    }
}
