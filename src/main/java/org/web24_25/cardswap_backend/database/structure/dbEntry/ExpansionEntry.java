package org.web24_25.cardswap_backend.database.structure.dbEntry;

import org.web24_25.cardswap_backend.database.structure.dbTables.CardsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.GamesTable;

import java.util.List;

public interface ExpansionEntry {
    Integer id();
    Integer game();
    String name();
    String description();
    boolean setName(String name);
    boolean setDescription(String description);
    default GameEntry getGame() {
        assert GamesTable.getInstance() != null;
        return GamesTable.getInstance().getGameWithId(game());
    };
    default List<CardEntry> getCards() {
        assert CardsTable.getInstance() != null;
        return CardsTable.getInstance().getCardsWithExpansion(id());
    };
}
