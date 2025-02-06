package org.web24_25.cardswap_backend.service;

import org.springframework.http.ResponseEntity;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.GamesTablePostgres;
import org.web24_25.cardswap_backend.requests.AddGame;

public class GameService {
    public ResponseEntity<String> getGames() {
        try {
            StringBuilder games = new StringBuilder("{ \"games\": [");
            for (var game : GamesTablePostgres.getInstance().getAllGames()) {
                String gameString = game.toJson() + ",";
                games.append(gameString);
            }
            if (games.toString().endsWith(",")) {
                games.replace(games.length() - 1, games.length(), "");
            }
            games.append("]}");
            return ResponseEntity.ok(games.toString());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error getting games\"}");
        }
    }

    public ResponseEntity<String> getGame(int id) {
        var game = GamesTablePostgres.getInstance().getGameWithId(id);
        if (game == null) {
            return ResponseEntity.badRequest().body("{\"result\":\"Game not found\"}");
        }
        return ResponseEntity.ok(game.toString());
    }

    public ResponseEntity<String> addGame(AddGame game) {
        try {
            GamesTablePostgres.getInstance().addGame(game);
            return ResponseEntity.ok("{\"result\":\"Game added\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error adding game\"}");
        }
    }

    public ResponseEntity<String> removeGame(int gameId) {
        try {
            GamesTablePostgres.getInstance().removeGameWithId(gameId);
            return ResponseEntity.ok("{\"result\":\"Game removed\"}");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("{\"result\":\"Error removing game\"}");
        }
    }
}
