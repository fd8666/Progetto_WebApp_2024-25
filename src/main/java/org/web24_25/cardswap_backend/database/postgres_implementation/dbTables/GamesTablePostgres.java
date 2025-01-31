package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.GamesTable;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class GamesTablePostgres implements GamesTable {
    private static final HashMap<Integer, GameEntry> games = new HashMap<>();
    private static GamesTablePostgres instance;
    public static final Logger logger = Logger.getLogger(GamesTablePostgres.class.getName());

    public static GamesTablePostgres getInstance() {
        if (instance == null) {
            instance = new GamesTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addGame(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO games (name) VALUES (?);");
                ps.setString(1, name);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeGameWithName(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                GameEntry ge = GamesTablePostgres.getInstance().getGameWithName(name);
                if (!ge.getExpansions().isEmpty()) {
//                    throw new IllegalStateException("Cannot remove game with expansions");
                    return false;
                }
                if (!ge.getCards().isEmpty()) {
//                    throw new IllegalStateException("Cannot remove game with cards");
                    return false;
                }

                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("DELETE FROM games WHERE name = ?;");
                ps.setString(1, name);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeGameWithId(int id) {
        return false;
    }

    @Override
    public GameEntry getGameWithName(String name) {
        return null;
    }

    @Override
    public GameEntry getGameWithId(int id) {
        return null;
    }

    @Override
    public List<ExpansionEntry> getExpansionsForGame(int id) {
        return List.of();
    }

    @Override
    public List<CardEntry> getCardsForGame(int id) {
        return List.of();
    }

    private GamesTablePostgres() {}
}
