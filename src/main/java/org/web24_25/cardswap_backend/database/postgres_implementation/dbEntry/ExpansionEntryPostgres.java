package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.CardsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.GamesTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class ExpansionEntryPostgres implements ExpansionEntry {
    private final Integer id;
    private final Integer game;
    private String name;
    private String description;

    private static final Logger logger = Logger.getLogger(ExpansionEntryPostgres.class.getName());

    public ExpansionEntryPostgres(Integer id, Integer game, String name, String description) {
        this.id = id;
        this.game = game;
        this.name = name;
        this.description = description;
    }

    public Integer id() {
        return id;
    }

    public Integer game() {
        return game;
    }

    public String name() {
        return name;
    }

    public String description() {
        return description;
    }

    @Override
    public boolean setName(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE expansions SET name = ? WHERE id = ?;");
                ps.setString(1, name);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.name = name;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean setDescription(String description) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE expansions SET description = ? WHERE id = ?;");
                ps.setString(1, description);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.description = description;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public GameEntry getGame() {
        return GamesTablePostgres.getInstance().getGameWithId(game());
    }

    @Override
    public List<CardEntry> getCards() {
        return CardsTablePostgres.getInstance().getCardsWithExpansion(id());
    }

    @Override
    public String toJson() {
        return "{\"id\":" + id + ",\"game\":" + game + ",\"name\":\"" + name + "\",\"description\":\"" + description + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (ExpansionEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.game, that.game) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, game, name, description);
    }

    @Override
    public String toString() {
        return "ExpansionEntryPostgres[" +
                "id=" + id + ", " +
                "game=" + game + ", " +
                "name=" + name + ", " +
                "description=" + description + ']';
    }
}
