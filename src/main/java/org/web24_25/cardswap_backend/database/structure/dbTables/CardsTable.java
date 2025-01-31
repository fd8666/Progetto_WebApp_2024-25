package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;

import java.util.List;

public interface CardsTable {
    static CardsTable getInstance() {
        return null;
    }
    boolean addCard(int gameId, int expansionId, String cardName, String identifier);
    boolean removeCardWithId(int cardId);
    boolean removeCardsWithExpansion(int expansionId);
    boolean removeCardsWithGame(int gameId);
    CardEntry getCardWithId(int cardId);
    List<CardEntry> getCardsWithExpansion(int expansionId);
    List<CardEntry> getCardsWithGame(int gameId);
    List<CardEntry> getCardsWithTag(int tagId);
    CardEntry getCardWithName(String cardName);
    CardEntry getCardWithIdentifier(String identifier);
}
