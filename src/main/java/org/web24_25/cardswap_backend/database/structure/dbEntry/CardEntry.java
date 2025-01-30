package org.web24_25.cardswap_backend.database.structure.dbEntry;

import org.web24_25.cardswap_backend.database.structure.dbTables.CardTagsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.GamesTable;

import java.util.List;

public interface CardEntry {
    Integer id();
    Integer game();
    String name();
    Integer expansion();
    String description();
    String identifier();
    boolean setName(String name);
    boolean setExpansion(Integer expansion);
    boolean setGame(Integer game);
    boolean setDescription(String description);
    boolean setIdentifier(String identifier);
    default GameEntry getGame() {
        assert GamesTable.getInstance() != null;
        return GamesTable.getInstance().getGameWithId(game());
    };
    default ExpansionEntry getExpansion() {
        assert ExpansionsTable.getInstance() != null;
        return ExpansionsTable.getInstance().getExpansionWithId(expansion());
    };
    default List<TagEntry> getTags() {
        assert CardTagsTable.getInstance() != null;
        return CardTagsTable.getInstance().getTagsWithCard(id());
    };
}
