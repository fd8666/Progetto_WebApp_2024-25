package org.web24_25.cardswap_backend.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.InventoryEntryPostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.*;
import org.web24_25.cardswap_backend.database.structure.dbEntry.InventoryEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.SessionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;

public class InventoryService {
    public InventoryService() {}

    private final static ResponseEntity<String> SESSION_NOT_FOUND = ResponseEntity.badRequest().body("{\"result\":\"Session not found\"}");
    private final static ResponseEntity<String> USER_NOT_FOUND = ResponseEntity.badRequest().body("{\"result\":\"User not found\"}");
    private final static ResponseEntity<String> ERROR_GETTING_CARDS = ResponseEntity.badRequest().body("{\"result\":\"Error getting cards\"}");

    private SessionEntry validateSession(HttpSession session) {
        String sessionId = (String) session.getAttribute("session_id");
        return SessionsTablePostgres.getInstance().getSessionWithCookie(sessionId);
    }

    private UserEntry validateUser(SessionEntry session) {
        return UsersTablePostgres.getInstance().getUserWithId(session.user_id());
    }

    public ResponseEntity<String> getCards(HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        try {
            StringBuilder cards = new StringBuilder("{ \"cards\": [");
            for (var inventories : InventoriesTablePostgres.getInstance().getInventoriesWithUser(userEntry.id())) {
                String card = "{" + "\"card\":" + inventories.card() + "," + "\"amount\":" + inventories.amount() + "},";
                cards.append(card);
            }
            if (cards.toString().endsWith(",")) {
                cards.replace(cards.length() - 1, cards.length(), "");
            }
            cards.append("]}");
            return ResponseEntity.ok(cards.toString());
        } catch (Exception e) {
            return ERROR_GETTING_CARDS;
        }
    }

    public ResponseEntity<String> decrementCard(int cardId, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        var entry = InventoriesTablePostgres.getInstance().getInventoryWithCardWithUser(cardId, userEntry.id());
        if (entry.decrementAmount(1)) {
            return ResponseEntity.ok("{\"result\":\""+entry.amount()+"\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error decrementing card\"}");
        }
    }

    public ResponseEntity<String> incrementCard(int cardId, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        var entry = InventoriesTablePostgres.getInstance().getInventoryWithCardWithUser(cardId, userEntry.id());
        if (entry.incrementAmount(1)) {
            return ResponseEntity.ok("{\"result\":\""+entry.amount()+"\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error incrementing card\"}");
        }
    }

    public ResponseEntity<String> decrementCardAmount(int cardId, int amount, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        var entry = InventoriesTablePostgres.getInstance().getInventoryWithCardWithUser(cardId, userEntry.id());
        if (entry.decrementAmount(amount)) {
            return ResponseEntity.ok("{\"result\":\""+entry.amount()+"\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error decrementing card\"}");
        }
    }

    public ResponseEntity<String> incrementCardAmount(int cardId, int amount, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        var entry = InventoriesTablePostgres.getInstance().getInventoryWithCardWithUser(cardId, userEntry.id());
        if (entry.incrementAmount(amount)) {
            return ResponseEntity.ok("{\"result\":\""+entry.amount()+"\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error incrementing card\"}");
        }
    }

    public ResponseEntity<String> addCard(int cardId, int amount, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        if (InventoriesTablePostgres.getInstance().addInventory(userEntry.id(), cardId, amount)) {
            return ResponseEntity.ok("{\"result\":\"Card added\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error adding card\"}");
        }
    }

    public ResponseEntity<String> removeCard(int cardId, HttpSession session) {
        SessionEntry sessionEntry = validateSession(session);
        if (sessionEntry == null) {
            return SESSION_NOT_FOUND;
        }

        UserEntry userEntry = validateUser(sessionEntry);
        if (userEntry == null) {
            return USER_NOT_FOUND;
        }

        var entry = InventoriesTablePostgres.getInstance().getInventoryWithCardWithUser(cardId, userEntry.id());
        if (entry.decrementAmount(entry.amount())) {
            return ResponseEntity.ok("{\"result\":\"Card removed\"}");
        } else {
            return ResponseEntity.badRequest().body("{\"result\":\"Error removing card\"}");
        }
    }
}
