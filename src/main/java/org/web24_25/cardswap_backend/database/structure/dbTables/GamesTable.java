package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;
import org.web24_25.cardswap_backend.requests.AddGame;
import org.web24_25.cardswap_backend.service.GameService;

import java.util.List;

public interface GamesTable {
    static GamesTable getInstance() {
        return null;
    }
    boolean addGame(String name);

    boolean addGame(AddGame game);

    boolean removeGameWithName(String name);
    boolean removeGameWithId(int id);
    GameEntry getGameWithName(String name);
    GameEntry getGameWithId(int id);

    List<GameEntry> getAllGames();
}
