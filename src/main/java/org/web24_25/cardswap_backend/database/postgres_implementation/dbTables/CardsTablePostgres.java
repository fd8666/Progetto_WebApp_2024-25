package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.CardEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class CardsTablePostgres implements CardsTable {
    private static final HashMap<Integer, CardEntryPostgres> cards = new HashMap<>();
    private static CardsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(CardsTablePostgres.class.getName());

    public static CardsTablePostgres getInstance() {
        if (instance == null) {
            instance = new CardsTablePostgres();
        }
        return instance;
    }

    private CardsTablePostgres() {}

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
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var itp = InventoriesTablePostgres.getInstance().getInventoriesWithCard(cardId);
                if (!itp.isEmpty())
                    return false;
                var ps = DatabasePostgres.conn.prepareStatement("DELETE FROM cards WHERE id = ?;");
                ps.setInt(1, cardId);
                if (ps.executeUpdate() != 0) {
                    cards.remove(cardId);
                    return true;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeCardsWithExpansion(int expansionId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var cwe = getCardsWithExpansion(expansionId);
                for (var card : cwe) {
                    if (!InventoriesTablePostgres.getInstance().getInventoriesWithCard(card.id()).isEmpty()) {
                        return false;
                    }
                }
                for (var card : cwe) {
                    removeCardWithId(card.id());
                }
                return true;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeCardsWithGame(int gameId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var cwg = getCardsWithGame(gameId);
                for (var card : cwg) {
                    if (!InventoriesTablePostgres.getInstance().getInventoriesWithCard(card.id()).isEmpty()) {
                        return false;
                    }
                }
                for (var card : cwg) {
                    removeCardWithId(card.id());
                }
                return true;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public CardEntry getCardWithId(int cardId) {
        if (cards.containsKey(cardId)) {
            return cards.get(cardId);
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM cards WHERE id = ?;");
                ps.setInt(1, cardId);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var card = new CardEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("game"),
                        rs.getInt("expansion"),
                        rs.getString("identifier"),
                        rs.getString("description")
                    );
                    cards.put(cardId, card);
                    return card;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<CardEntry> getCardsWithExpansion(int expansionId) {
        ArrayList<CardEntry> cards_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM cards WHERE expansion = ?;");
                ps.setInt(1, expansionId);
                var rs = ps.executeQuery();
                while (rs.next()) {
                    getCardWithId(rs.getInt("id"));
                    cards_list.add(cards.get(rs.getInt("id")));
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return cards_list;
    }

    @Override
    public List<CardEntry> getCardsWithGame(int gameId) {
        ArrayList<CardEntry> cards_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM cards WHERE game = ?;");
                ps.setInt(1, gameId);
                var rs = ps.executeQuery();
                while (rs.next()) {
                    getCardWithId(rs.getInt("id"));
                    cards_list.add(cards.get(rs.getInt("id")));
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return cards_list;
    }

    @Override
    public List<CardEntry> getCardsWithTag(int tagId) {
        ArrayList<CardEntry> cards_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM card_tags WHERE tag = ?;");
                ps.setInt(1, tagId);
                var rs = ps.executeQuery();
                while (rs.next()) {
                    getCardWithId(rs.getInt("id"));
                    cards_list.add(cards.get(rs.getInt("id")));
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return cards_list;
    }

    @Override
    public CardEntry getCardWithName(String cardName) {
        for (var card : cards.values()) {
            if (card.name().equals(cardName)) {
                return card;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM cards WHERE name = ?;");
                ps.setString(1, cardName);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var card = new CardEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("game"),
                        rs.getInt("expansion"),
                        rs.getString("identifier"),
                        rs.getString("description")
                    );
                    cards.put(card.id(), card);
                    return card;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public CardEntry getCardWithIdentifier(String identifier) {
        for (var card : cards.values()) {
            if (card.identifier().equals(identifier)) {
                return card;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM cards WHERE identifier = ?;");
                ps.setString(1, identifier);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var card = new CardEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("game"),
                        rs.getInt("expansion"),
                        rs.getString("identifier"),
                        rs.getString("description")
                    );
                    cards.put(card.id(), card);
                    return card;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }
}
