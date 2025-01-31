package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardTagsTable;

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
    public boolean addTagToCard(int cardId, int tagId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO card_tags (card, tag) VALUES (?, ?);");
                ps.setInt(1, cardId);
                ps.setInt(2, tagId);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
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

    private CardTagsTablePostgres() {}
}
