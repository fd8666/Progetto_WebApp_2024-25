package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.CardEntryPostgres;
import org.web24_25.cardswap_backend.requests.AddCard;
import org.web24_25.cardswap_backend.service.CardService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/cards")
public class CardController {
    private final CardService cardService = new CardService();

//    @GetMapping("/get?id={id}")
    @GetMapping("/getCard/{id}")
    public ResponseEntity<?> getCard(@PathVariable int id) {
        ResponseEntity<String> card = cardService.getCard(id);
        return ResponseEntity.ok().body(card);
    }

    @GetMapping("/getCards")
    public ResponseEntity<String> getCard() {
        return cardService.getCards();
    }

    @GetMapping("/getCardsRange/{start}/{limit}")
    public ResponseEntity<String> getCardsRange(@PathVariable int start, @PathVariable int limit) {
        return cardService.getCardsRange(start, limit);
    }

    @PostMapping("/addCard")
    public ResponseEntity<String> addCard(@RequestBody AddCard card) {
        return cardService.addCard(card);
    }

    @GetMapping("/removeCard/{cardId}")
    public ResponseEntity<String> removeCard(@PathVariable int cardId) {
        return cardService.removeCard(cardId);
    }
}
