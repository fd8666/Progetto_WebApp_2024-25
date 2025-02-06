package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.ExpansionsTablePostgres;
import org.web24_25.cardswap_backend.requests.AddExpansion;

public class ExpansionService {
    public ResponseEntity<String> getExpansions() {
        try {
            StringBuilder expansions = new StringBuilder("{ \"expansions\": [");
            for (var expansion : ExpansionsTablePostgres.getInstance().getAllExpansions()) {
                String expansionString = expansion.toJson() + ",";
                expansions.append(expansionString);
            }
            if (expansions.toString().endsWith(",")) {
                expansions.replace(expansions.length() - 1, expansions.length(), "");
            }
            expansions.append("]}");
            return ResponseEntity.ok(expansions.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting expansions\"}");
        }
    }

    public ResponseEntity<String> getExpansion(int id) {
        var expansion = ExpansionsTablePostgres.getInstance().getExpansionWithId(id);
        if (expansion == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting expansions\"}");
        }
        return ResponseEntity.ok("{\"result\":\""+expansion.toJson()+"\"}");
    }

    public ResponseEntity<String> addExpansion(AddExpansion expansion) {
        try {
            ExpansionsTablePostgres.getInstance().addExpansion(expansion);
            return ResponseEntity.ok("{\"result\":\"Expansion added\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error adding expansion\"}");
        }
    }

    public ResponseEntity<String> removeExpansion(int id) {
        try {
            ExpansionsTablePostgres.getInstance().removeExpansionWithId(id);
            return ResponseEntity.ok("{\"result\":\"Expansion removed\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error removing expansion\"}");
        }
    }
}
