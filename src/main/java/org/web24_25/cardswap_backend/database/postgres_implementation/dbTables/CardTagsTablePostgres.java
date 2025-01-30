package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardTagsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class CardTagsTablePostgres implements CardTagsTable {
    private static CardTagsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(CardTagsTablePostgres.class.getName());

    public static CardTagsTablePostgres getInstance() {
        if (instance == null) {
            instance = new CardTagsTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addCardTagToTable(int cardId, int tagId) {
        return false;
    }

    @Override
    public boolean removeCardTagFromTable(int cardId, int tagId) {
        return false;
    }

    @Override
    public boolean removeAllCardTagsFromCard(int cardId) {
        return false;
    }

    @Override
    public boolean removeAllCardTagsFromTag(int tagId) {
        return false;
    }

    @Override
    public List<CardEntry> getCardsWithTag(int tagId) {
        return List.of();
    }

    @Override
    public List<TagEntry> getTagsWithCard(int cardId) {
        return List.of();
    }

    private CardTagsTablePostgres() {}
}
