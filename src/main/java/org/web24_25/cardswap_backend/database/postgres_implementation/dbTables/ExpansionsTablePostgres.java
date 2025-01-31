package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.ExpansionEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.ExpansionsTable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class ExpansionsTablePostgres implements ExpansionsTable {
    private static final HashMap<Integer, ExpansionEntry> expansions = new HashMap<>();
    private static ExpansionsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(ExpansionsTablePostgres.class.getName());

    public static ExpansionsTablePostgres getInstance() {
        if (instance == null) {
            instance = new ExpansionsTablePostgres();
        }
        return instance;
    }

    @Override
    public boolean addExpansion(String name, int gameId) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("INSERT INTO expansions (game, name) VALUES (?, ?);");
                ps.setInt(1, gameId);
                ps.setString(2, name);
                return ps.executeUpdate() != 0;
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeExpansionWithName(String name) {
        return removeExpansionWithId(getExpansionWithName(name).id());
    }

    @Override
    public boolean removeExpansionWithId(int id) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                if (CardsTablePostgres.getInstance().getCardsWithExpansion(id).isEmpty()) {
                    var ps = DatabasePostgres.conn.prepareStatement("DELETE FROM expansions WHERE id = ?;");
                    ps.setInt(1, id);
                    if (ps.executeUpdate() != 0) {
                        expansions.remove(id);
                        return true;
                    }
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public ExpansionEntry getExpansionWithName(String name) {
        for (ExpansionEntry entry : expansions.values()) {
            if (entry.name().equals(name)) {
                return entry;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM expansions WHERE name = ?;");
                ps.setString(1, name);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var entry = new ExpansionEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("game"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    expansions.put(entry.id(), entry);
                    return entry;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public ExpansionEntry getExpansionWithId(int id) {
        if (expansions.containsKey(id)) {
            return expansions.get(id);
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM expansions WHERE id = ?;");
                ps.setInt(1, id);
                var rs = ps.executeQuery();
                if (rs.next()) {
                    var entry = new ExpansionEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("game"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    expansions.put(entry.id(), entry);
                    return entry;
                }
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public List<ExpansionEntry> getExpansionsWithGame(int gameId) {
        ArrayList<ExpansionEntry> expansions_list = new ArrayList<>();
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                var ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM expansions WHERE game = ?;");
                ps.setInt(1, gameId);
                var rs = ps.executeQuery();
                while (rs.next()) {
                    if (expansions.containsKey(rs.getInt("id"))) {
                        expansions_list.add(expansions.get(rs.getInt("id")));
                        continue;
                    }
                    var entry = new ExpansionEntryPostgres(
                        rs.getInt("id"),
                        rs.getInt("game"),
                        rs.getString("name"),
                        rs.getString("description")
                    );
                    expansions.put(entry.id(), entry);
                    expansions_list.add(entry);
                }
                rs.close();
            } catch (Exception e) {
                logger.severe(e.getMessage());
            }
        }
        return expansions_list;
    }

    private ExpansionsTablePostgres() {}
}
