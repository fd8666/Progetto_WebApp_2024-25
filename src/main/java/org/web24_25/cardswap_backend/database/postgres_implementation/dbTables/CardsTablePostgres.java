package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardTagsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class CardsTablePostgres implements CardsTable {
    private static final HashMap<Integer, CardTagsTable> cards = new HashMap<>();
    private static CardsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(CardsTablePostgres.class.getName());

    public static CardsTablePostgres getInstance() {
        if (instance == null) {
            instance = new CardsTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addCard(int gameId, int expansionId, String cardName, String identifier) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO cards (game, expansion, name, identifier) VALUES (?, ?, ?, ?);");
                ps.setInt(1, gameId);
                ps.setInt(2, expansionId);
                ps.setString(2, cardName);
                ps.setString(2, identifier);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeCardWithId(int cardId) {
        return false;
    }

    @Override
    public boolean removeCardsWithExpansion(int expansionId) {
        return false;
    }

    @Override
    public boolean removeCardsWithGame(int gameId) {
        return false;
    }

    @Override
    public CardEntry getCardWithId(int cardId) {
        return null;
    }

    @Override
    public List<CardEntry> getCardsWithExpansion(int expansionId) {
        return List.of();
    }

    @Override
    public List<CardEntry> getCardsWithGame(int gameId) {
        return List.of();
    }

    @Override
    public CardEntry getCardWithName(String cardName) {
        return null;
    }

    @Override
    public CardEntry getCardWithIdentifier(String identifier) {
        return null;
    }

    private CardsTablePostgres() {}
}
