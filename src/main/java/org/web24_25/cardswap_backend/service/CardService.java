package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.CardsTablePostgres;
import org.web24_25.cardswap_backend.requests.AddCard;

public class CardService {
    public ResponseEntity<String> getCard(int id) {
        var card = CardsTablePostgres.getInstance().getCardWithId(id);
        if (card == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Card not found\"}");
        }
        return ResponseEntity.ok(card.toString());
    }

    public ResponseEntity<String> getCards() {
        try {
            StringBuilder cards = new StringBuilder("{ \"cards\": [");
            for (var card : CardsTablePostgres.getInstance().getAllCards()) {
                String cardString = card.toJson() + ",";
                cards.append(cardString);
            }
            if (cards.toString().endsWith(",")) {
                cards.replace(cards.length() - 1, cards.length(), "");
            }
            cards.append("]}");
            return ResponseEntity.ok(cards.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting cards\"}");
        }
    }

    public ResponseEntity<String> getCardsRange(int start, int limit) {
        try {
            StringBuilder cards = new StringBuilder("{ \"cards\": [");
            for (var card : CardsTablePostgres.getInstance().getCardsFromIdWithLimit(start, limit)) {
                String cardString = card.toJson() + ",";
                cards.append(cardString);
            }
            if (cards.toString().endsWith(",")) {
                cards.replace(cards.length() - 1, cards.length(), "");
            }
            cards.append("]}");
            return ResponseEntity.ok(cards.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting cards\"}");
        }
    }

    public ResponseEntity<String> addCard(AddCard card) {
        try {
            CardsTablePostgres.getInstance().addCard(card);
            return ResponseEntity.ok("{\"result\":\"Card added\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error adding card\"}");
        }
    }

    public ResponseEntity<String> removeCard(int cardId) {
        try {
            CardsTablePostgres.getInstance().removeCardWithId(cardId);
            return ResponseEntity.ok("{\"result\":\"Card removed\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error removing card\"}");
        }
    }
}