package org.web24_25.cardswap_backend.database.postgres_implementation.dbTables;

import org.web24_25.cardswap_backend.database.postgres_implementation.DatabasePostgres;
import org.web24_25.cardswap_backend.database.structure.dbEntry.TagEntry;
import org.web24_25.cardswap_backend.database.structure.dbEntry.UserEntry;
import org.web24_25.cardswap_backend.database.structure.dbTables.TagsTable;

import java.sql.PreparedStatement;
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
        return false;
    }

    @Override
    public boolean removeTagWithId(int id) {
        return false;
    }

    @Override
    public TagEntry getTagWithName(String name) {
        return null;
    }

    @Override
    public TagEntry getTagWithId(int id) {
        return null;
    }
}
