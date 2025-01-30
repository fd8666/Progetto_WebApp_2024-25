package org.web24_25.cardswap_backend.database.structure.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;

import java.util.List;

public interface CardTagsTable {
    static CardTagsTable getInstance() {
        return null;
    }
    boolean addCardTagToTable(int cardId, int tagId);
    boolean removeCardTagFromTable(int cardId, int tagId);
    boolean removeAllCardTagsFromCard(int cardId);
    boolean removeAllCardTagsFromTag(int tagId);
    List<CardEntry> getCardsWithTag(int tagId);
    List<TagEntry> getTagsWithCard(int cardId);
}
