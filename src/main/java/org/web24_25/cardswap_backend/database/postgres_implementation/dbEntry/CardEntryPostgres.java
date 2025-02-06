package org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.CardTagsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.ExpansionsTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.GamesTablePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbTables.TagsTablePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.CardEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.ExpansionEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.GameEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public final class CardEntryPostgres implements CardEntry {
    private final Integer id;
    private String name;
    private Integer game;
    private Integer expansion;
    private String identifier;
    private String description;

    private static final Logger logger = Logger.getLogger(CardEntryPostgres.class.getName());

    public CardEntryPostgres(Integer id, String name, Integer game, Integer expansion, String identifier, String description) {
        this.id = id;
        this.name = name;
        this.game = game;
        this.expansion = expansion;
        this.identifier = identifier;
        this.description = description;
    }

    public Integer id() {
        return id;
    }

    public String name() {
        return name;
    }

    public Integer game() {
        return game;
    }

    public Integer expansion() {
        return expansion;
    }

    public String identifier() {
        return identifier;
    }

    @Override
    public boolean setName(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE cards SET name = ? WHERE id = ?;");
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
    public boolean setExpansion(Integer expansion) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE cards SET expansion = ? WHERE id = ?;");
                ps.setInt(1, expansion);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.expansion = expansion;
                    return true;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean setGame(Integer game) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE cards SET game = ? WHERE id = ?;");
                ps.setInt(1, game);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.game = game;
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
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE cards SET description = ? WHERE id = ?;");
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
    public boolean setIdentifier(String identifier) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("UPDATE cards SET identifier = ? WHERE id = ?;");
                ps.setString(1, identifier);
                ps.setInt(2, this.id);
                int res = ps.executeUpdate();
                if (res != 0) {
                    this.identifier = identifier;
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
    public ExpansionEntry getExpansion() {
        return ExpansionsTablePostgres.getInstance().getExpansionWithId(expansion());
    }

    @Override
    public List<TagEntry> getTags() {
        return TagsTablePostgres.getInstance().getTagsForCard(id());
    }

    @Override
    public boolean addTag(TagEntry tag) {
        return CardTagsTablePostgres.getInstance().addTagToCard(id(), tag.id());
    }

    @Override
    public boolean removeTag(TagEntry tag) {
        return CardTagsTablePostgres.getInstance().removeTagFromCard(id(), tag.id());
    }

    public String description() {
        return description;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (CardEntryPostgres) obj;
        return Objects.equals(this.id, that.id) &&
                Objects.equals(this.name, that.name) &&
                Objects.equals(this.game, that.game) &&
                Objects.equals(this.expansion, that.expansion) &&
                Objects.equals(this.identifier, that.identifier) &&
                Objects.equals(this.description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, game, expansion, identifier, description);
    }

    @Override
    public String toString() {
        return "CardEntryPostgres[" +
                "id=" + id + ", " +
                "name=" + name + ", " +
                "game=" + game + ", " +
                "expansion=" + expansion + ", " +
                "identifier=" + identifier + ", " +
                "description=" + description + ']';
    }

    @Override
    public String toJson() {
        return "{" +
                "\"id\":" + id + "," +
                "\"name\":\"" + name + "\"," +
                "\"game\":" + game + "," +
                "\"expansion\":" + expansion + "," +
                "\"identifier\":\"" + identifier + "\"," +
                "\"description\":\"" + description + "\"" +
                "}";
    }
}
