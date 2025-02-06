package org.web24_25.cardswap_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.web24_25.cardswap_backend.requests.AddGame;
import org.web24_25.cardswap_backend.service.GameService;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService = new GameService();

    @GetMapping("/getGames")
    public ResponseEntity<String> getGames() {
        return gameService.getGames();
    }

    @GetMapping("/getGame/{id}")
    public ResponseEntity<String> getGame(@PathVariable int id) {
        return gameService.getGame(id);
    }

    @PostMapping("/addGame")
    public ResponseEntity<String> addGame(@RequestBody AddGame game) {
        return gameService.addGame(game);
    }

    @GetMapping("/removeGame/{gameId}")
    public ResponseEntity<String> removeGame(@PathVariable int gameId) {
        return gameService.removeGame(gameId);
    }
}
