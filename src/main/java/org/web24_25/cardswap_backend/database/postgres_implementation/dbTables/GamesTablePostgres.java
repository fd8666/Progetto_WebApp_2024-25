package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.GameEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.GamesTable;
import org.web24_25.cardswap_backend.requests.AddGame;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
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

    private GamesTablePostgres() {}

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
    public boolean addGame(AddGame game) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO games (name) VALUES (?);");
                ps.setString(1, game.name());
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
                if (ps.executeUpdate() != 0) {
                    games.remove(ge.id());
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeGameWithId(int id) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                GameEntry ge = GamesTablePostgres.getInstance().getGameWithId(id);
                if (!ge.getExpansions().isEmpty()) {
//                    throw new IllegalStateException("Cannot remove game with expansions");
                    return false;
                }
                if (!ge.getCards().isEmpty()) {
//                    throw new IllegalStateException("Cannot remove game with cards");
                    return false;
                }

                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("DELETE FROM games WHERE id = ?;");
                ps.setInt(1, id);
                if (ps.executeUpdate() != 0) {
                    games.remove(id);
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public GameEntry getGameWithName(String name) {
        for (GameEntry ge : games.values()) {
            if (ge.name().equals(name)) {
                return ge;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM games WHERE name = ?;");
                ps.setString(1, name);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var gep = new GameEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    games.put(gep.id(), gep);
                    return gep;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public GameEntry getGameWithId(int id) {
        if (games.containsKey(id)) {
            return games.get(id);
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM games WHERE id = ?;");
                ps.setInt(1, id);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var gep = new GameEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    games.put(gep.id(), gep);
                    return gep;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<GameEntry> getAllGames() {
        ArrayList<GameEntry> gameEntries = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM games;");
                var rs = ps.executeQuery();
                while (rs.next()) {
                    if (games.containsKey(rs.getInt("id"))) {
                        gameEntries.add(games.get(rs.getInt("id")));
                        continue;
                    }
                    var gep = new GameEntryPostgres(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    games.put(gep.id(), gep);
                    gameEntries.add(gep);
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return gameEntries;
    }
}
