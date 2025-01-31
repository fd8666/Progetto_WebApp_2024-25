package org.web24_25.cardswap_backend.database.structure.dbTables;

public interface CardTagsTable {
    static CardTagsTable getInstance() {
        return null;
    }
    boolean addTagToCard(int cardId, int tagId);
    boolean removeTagFromCard(int cardId, int tagId);
    boolean removeAllTagsFromCard(int cardId);
    boolean removeTagFromAllCards(int tagId);
}
