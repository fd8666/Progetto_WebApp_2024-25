package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Logger;

public final class GameEntryPostgres implements GameEntry {
    private final Integer id;
    private String name;
    private String description;

    private static final Logger logger = Logger.getLogger(GameEntryPostgres.class.getName());

    public GameEntryPostgres(Integer id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Integer id() {
        return id;
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
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE games SET name = ? WHERE id = ?;");
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
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE games SET description = ? WHERE id = ?;");
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
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (GameEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description);
    }

    @Override
    public String toString() {
        return "GameEntryPostgres[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "description=" + description + ']';
    }

}
