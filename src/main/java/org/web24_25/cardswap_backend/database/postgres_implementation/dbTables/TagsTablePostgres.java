package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.postgres_implementation.dbEntry.TagEntryPostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.CardTagsTable;
import org.web24_25.cardswap_backend.database.structure.dbTables.TagsTable;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

public class TagsTablePostgres implements TagsTable {
    private static final HashMap<Integer, TagEntry> tags = new HashMap<>();
    private static TagsTablePostgres instance;
    public static final Logger logger = Logger.getLogger(TagsTablePostgres.class.getName());

    public static TagsTablePostgres getInstance() {
        if (instance == null) {
            instance = new TagsTablePostgres();
        }
        return instance;
    }

    private TagsTablePostgres() {}

    @Override
    public boolean addTag(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("INSERT INTO tags (name) VALUES (?);");
                ps.setString(1, name);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeTagWithName(String name) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                assert TagsTable.getInstance() != null;
                TagEntry te = TagsTable.getInstance().getTagWithName(name);
                assert CardTagsTable.getInstance() != null;
                if (!CardTagsTable.getInstance().getCardsWithTag(te.id()).isEmpty()) {
                    return false;
                }

                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("DELETE FROM tags WHERE name = ?;");
                ps.setString(1, name);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean removeTagWithId(int id) {
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                assert CardTagsTable.getInstance() != null;
                if (!CardTagsTable.getInstance().getCardsWithTag(id).isEmpty()) {
                    return false;
                }

                PreparedStatement ps =  DatabasePostgres.conn.prepareStatement("DELETE FROM tags WHERE id = ?;");
                ps.setInt(1, id);
                return ps.executeUpdate() != 0;
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return false;
    }

    @Override
    public TagEntry getTagWithName(String name) {
        for (TagEntry te : tags.values()) {
            if (te.name().equals(name)) {
                return te;
            }
        }
        if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
            try {
                PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM tags WHERE name = ?;");
                ps.setString(1, name);
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    TagEntryPostgres tep = new TagEntryPostgres(
                            rs.getInt("id"),
                            rs.getString("name")
                    );
                    tags.put(tep.id(), tep);
                    rs.close();
                    return tep;
                }
            } catch (SQLException e) {
                logger.severe(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public TagEntry getTagWithId(int id) {
        if (tags.containsKey(id)) {
            return tags.get(id);
        } else {
            if (DatabasePostgres.getInstance().verifyConnectionAndReconnect()) {
                try {
                    PreparedStatement ps = DatabasePostgres.conn.prepareStatement("SELECT * FROM tags WHERE id = ?;");
                    ps.setInt(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        TagEntryPostgres tep = new TagEntryPostgres(
                            rs.getInt("id"),
                            rs.getString("name")
                        );
                        tags.put(tep.id(), tep);
                        rs.close();
                        return tep;
                    }
                } catch (SQLException e) {
                    logger.severe(e.getMessage());
                }
            }
        }
        return null;
    }
}
